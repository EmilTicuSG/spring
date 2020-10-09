package victor.training.spring.web.spring.feed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import victor.training.spring.web.feed.FeedProcessor;
import victor.training.spring.web.feed.IFileRepo;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedProcessorWithMockTest {

   @InjectMocks
   private FeedProcessor feedProcessor;
   @Mock
   private IFileRepo fileRepoMock;

   @Test
   public void oneFileWithOneLine() {
      when(fileRepoMock.getFileNames()).thenReturn(Arrays.asList("one.txt"));
      when(fileRepoMock.openFile("one.txt")).thenReturn(Stream.of("one"));
      assertThat(feedProcessor.countPendingLines()).isEqualTo(1);
   }

   @Test
   public void oneFileWith2Lines() {
      when(fileRepoMock.getFileNames()).thenReturn(Arrays.asList("two.txt"));
      when(fileRepoMock.openFile("two.txt")).thenReturn(Stream.of("one","two"));
      assertThat(feedProcessor.countPendingLines()).isEqualTo(2);
   }

   @Test
   public void twoFilesWith3Lines() {
      when(fileRepoMock.getFileNames()).thenReturn(Arrays.asList("one.txt", "two.txt"));
      when(fileRepoMock.openFile("one.txt")).thenReturn(Stream.of("one"));
      when(fileRepoMock.openFile("two.txt")).thenReturn(Stream.of("one","two"));
      assertThat(feedProcessor.countPendingLines()).isEqualTo(3);
   }

   // TODO IMAGINE EXTRA DEPENDENCY
}
