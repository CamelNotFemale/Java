package digital.nedra.counter;

import digital.nedra.counter.entities.Click;
import digital.nedra.counter.repository.ClickerRepository;
import digital.nedra.counter.services.ClickerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CounterApplicationTests {

    @Autowired
    ClickerService clickerService;

    @MockBean
    ClickerRepository clickerRepository;

    @Test
    void doClick() {
        Click click = new Click();
        click.setCount(5);
        Mockito.doReturn(Optional.of(click))
                .when(clickerRepository)
                .findById(1);
        Click newClick = clickerService.increment();
        Mockito.verify(clickerRepository, Mockito.times(1)).findById(1);
        assertEquals(newClick.getCount(), 6);
    }
}
