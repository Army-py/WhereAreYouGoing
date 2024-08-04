package fr.army.whereareyougoing.database.repository.callback;

public interface AsyncCallBackObject<T> {

  void done(T result);
}
