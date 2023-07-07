package alexdev.mp3.validator;

public interface Validator<T>{
    boolean validate(T t);
}
