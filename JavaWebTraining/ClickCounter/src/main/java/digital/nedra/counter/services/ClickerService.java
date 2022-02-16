package digital.nedra.counter.services;

import digital.nedra.counter.entities.Click;
import digital.nedra.counter.repository.ClickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClickerService {

        @Autowired
        private ClickerRepository clickerRepository;

        public Click getCounter() {
            Optional<Click> clicks = clickerRepository.findById(1);
            Click click = null;
            if (clicks.isEmpty()) {
                click = new Click();
                click.setId(1);
                click.setCount(0);
                clickerRepository.save(click);
            }
            else click = clicks.get();

            return click;
        }

        @Transactional(isolation = Isolation.REPEATABLE_READ)
        public Click increment() {
            Click click = clickerRepository.findById(1).get();
            click.setCount(click.getCount()+1);
            clickerRepository.save(click);

            return click;
        }
}
