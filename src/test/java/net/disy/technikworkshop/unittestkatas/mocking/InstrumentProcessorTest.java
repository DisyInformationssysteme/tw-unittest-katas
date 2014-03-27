package net.disy.technikworkshop.unittestkatas.mocking;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.*;

import org.junit.*;

public class InstrumentProcessorTest {
  private final ITaskDispatcher dispatcher = mock(ITaskDispatcher.class);
  private final IInstrument instrument = mock(IInstrument.class);
  private final InstrumentProcessor processor = new InstrumentProcessor(dispatcher, instrument);
  private final MockInstrument sucInstrument = new MockInstrument(true);
  private final MockInstrument errInstrument = new MockInstrument(false);

  @Test
  public void processWithAnyTask() throws Exception {
    processor.process();
    verify(dispatcher).getTask();
    verify(instrument).execute(anyString());
  }

  @Test
  public void processWithDefinedTask() throws Exception {
    when(dispatcher.getTask()).thenReturn("foo", "bar", "foo2");
    processor.process();
    verify(dispatcher).getTask();
    verify(instrument).execute("foo");
  }

  @Test(expected = IllegalArgumentException.class)
  public void processWithUndefinedTask() throws Exception {
    when(dispatcher.getTask()).thenReturn(null);
    doThrow(new IllegalArgumentException()).when(instrument).execute(null);
    processor.process();
  }

  @Test
  public void processWithFinishedTask() {
    String taskName = "finishingTask";
    InstrumentProcessor processor = createProcessor(sucInstrument, taskName);
    processor.process();
    verify(dispatcher).finishedTask(taskName);
  }

  private InstrumentProcessor createProcessor(IInstrument mockInstrument, String taskName) {
    InstrumentProcessor processor = new InstrumentProcessor(dispatcher, mockInstrument);
    mockInstrument.addListener(processor);
    when(dispatcher.getTask()).thenReturn(taskName);
    return processor;
  }

  @Test
  public void processWithNotFinishedTask() throws Exception {
    String taskName = "notFinishingTask";
    InstrumentProcessor processor = createProcessor(errInstrument, taskName);
    processor.process();
    verify(dispatcher).getTask();
    verifyNoMoreInteractions(dispatcher);
  }

  @Test
  public void processWithErrorTask() throws Exception {
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(byteArrayStream));
    String taskName = "notFinishingTask";
    InstrumentProcessor processor = createProcessor(errInstrument, taskName);
    processor.process();
    assertEquals("Error occurred", byteArrayStream.toString());
  }
}