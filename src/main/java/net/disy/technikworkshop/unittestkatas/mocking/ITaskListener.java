package net.disy.technikworkshop.unittestkatas.mocking;

public interface ITaskListener {
  void finished(String task);

  void error(String task);
}
