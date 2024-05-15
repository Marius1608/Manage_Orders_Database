package bll.validators;
/**
 * Interfata pentru validatori
 * @param <T> Tipul obiectului de validat
 *
 */
public interface Validator<T> {

	/**
	 * Valideaza un obiect de tip T
	 * @param t Obiectul de validat
	 */
	public void validate(T t);
}
