package dao;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;

/**
 * Clasa abstracta pentru operatiile de baza de acces la date
 *
 * @param <T> Tipul obiectului cu care lucreaza clasa
 */

public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    /**
     * Creeaza o interogare SELECT pentru un anumit camp
     *
     * @param field Numele campului după care se face interogarea SELECT
     * @return Șirul de caractere care reprezinta interogarea SELECT generata
     */
    private String createSelectQuery(String field) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creeaza o interogare SELECT pentru a obtine toate inregistrarile din tabel
     *
     * @return Sirul de caractere care reprezinta interogarea SELECT generata
     */
    private String createSelectAllQuery() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Returneaza o entitate bazata pe un ID dat
     * Extrage entitatea din baza de date folosind interogarea SELECT generata de createSelectQuery()
     * Utilizeaza metoda createObjects() pentru a crea entitatea
     * In cazul in care nu exista nicio inregistrare cu ID-ul dat, returneaza null
     *
     * @param id ID-ul obiectului de cautat
     * @return Obiectul gasit sau null daca nu exista
     */
    public T findById(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creeaza obiectele pe baza datelor din ResultSet
     * Converteste rezultatul unei interogari SELECT intr-o lista de obiecte de tipul generic T
     * Acesta este utilizata pentru a crea obiectele bazate pe datele din baza de date, fie folosind constructorii, fie folosind metodele setter
     *
     * @param resultSet ResultSet-ul din care se creeaza obiectele
     * @return Lista de obiecte create
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == type.getDeclaredFields().length)
                break;
        }
        if (ctor != null) {
            try {
                while (resultSet.next()) {
                    ctor.setAccessible(true);
                    Object[] objects = new Object[type.getDeclaredFields().length];
                    int i = 0;
                    for (Field field : type.getDeclaredFields()) {
                        String fieldName = field.getName();
                        objects[i] = resultSet.getObject(fieldName);
                        i++;
                    }
                    T instance = (T) ctor.newInstance(objects);
                    list.add(instance);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }else {
            return createObjectsWithSetters(resultSet);
        }
    }


    /**
     * Creeaza obiectele folosind setterii obiectului
     * Similara cu createObjects(), dar utilizeaza metodele setter ale obiectului pentru a seta valorile campurilor, în loc să utilizeze constructorii
     * @param resultSet ResultSet-ul din care se creeaza obiectele
     * @return Lista de obiecte create
     */
    private List<T> createObjectsWithSetters(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    if (method != null) {
                        method.invoke(instance, value);
                    }
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Insereaza o noua inregistrare în baza de date, utilizand valorile campurilor obiectului generic T dat ca parametru
     * Returneaza obiectul cu ID-ul generat
     *
     * @param t Obiectul de inserat
     * @return Obiectul inserat cu ID-ul actualizat (daca este cazul)
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            String tableName = type.getSimpleName();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(tableName).append(" (");

            Field[] fields = type.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(fields[i].getName());
            }
            sb.append(") VALUES (");

            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("?");
            }
            sb.append(")");
            String query = sb.toString();

            statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(parameterIndex++, field.get(t));
            }

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
                Field idField = type.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(t, generatedId);
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Sterge un obiect din baza de date după ID-ul sau
     *
     * @param id ID-ul obiectului de șters
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            String tableName = type.getSimpleName();
            String query = "DELETE FROM " + tableName + " WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Actualizeaza un obiect în baza de date
     *
     * @param t Obiectul de actualizat
     */
    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            String tableName = type.getSimpleName();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(tableName).append(" SET ");

            Field[] fields = type.getDeclaredFields();
            List<String> columnNames = new ArrayList<>();
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    columnNames.add(field.getName() + " = ?");
                }
            }
            sb.append(String.join(", ", columnNames));
            sb.append(" WHERE id = ?");
            String query = sb.toString();

            statement = connection.prepareStatement(query);

            int parameterIndex = 1;
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    statement.setObject(parameterIndex++, field.get(t));
                }
            }
            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            statement.setObject(parameterIndex, idField.get(t));

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Gaseste un obiect dupa numele sau
     *
     * @param name Numele obiectului de cautat
     * @return Obiectul gasit sau null daca nu exista
     */
    public T getByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

                return createObjects(resultSet).get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:getByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Returneaza toate inregistrarile din tabel sub forma de lista
     *
     * @return Lista de obiecte din tabel
     */
    public List<T> getAll() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query=createSelectAllQuery();
        List<T> resultList = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            resultList = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving all records from " + type.getName() + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return resultList;
    }

}
