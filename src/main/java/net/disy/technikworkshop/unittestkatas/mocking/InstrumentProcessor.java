package net.disy.technikworkshop.unittestkatas.mocking;

public class InstrumentProcessor implements IInstrumentProcessor, ITaskListener {

  private final ITaskDispatcher dispatcher;
  private final IInstrument instrument;

  public InstrumentProcessor(ITaskDispatcher dispatcher, IInstrument instrument) {
    this.dispatcher = dispatcher;
    this.instrument = instrument;
  }

  public void process() {
    String task = dispatcher.getTask();
    instrument.execute(task);
  }

  public void finished(String task) {
    dispatcher.finishedTask(task);
  }

  public void error(String task) {
    System.out.print("Error occurred");
  }

}
