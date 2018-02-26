package com.poe.trajetfacile.business;

import com.poe.trajetfacile.business.delegate.BookingDelegateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingDelegateServiceTests {

    @Test
    public void shouldBeCancelable() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelableDate = now.plusHours(BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS + 1);
        assertThat(BookingDelegateService.isCancelable(now, cancelableDate)).isTrue();
    }

    @Test
    public void shouldNotBeCancelable() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelableDate = now.plusHours(BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS - 1);
        assertThat(BookingDelegateService.isCancelable(now, cancelableDate)).isFalse();
    }

    @Test
    public void shouldNotBeCancelableWithDateInFuture() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelableDate = now.minusHours(BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS + 1);
        assertThat(BookingDelegateService.isCancelable(now, cancelableDate)).isFalse();
    }

    @Test
    public void shouldBeCancelableWithExactDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelableDate = now.plusHours(BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS);
        assertThat(BookingDelegateService.isCancelable(now, cancelableDate)).isFalse();
    }

    @Test
    public void shouldBeCancelableWithDateInMinutePrecision() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelableDate = now.plusHours(BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS).plusMinutes(10);
        assertThat(BookingDelegateService.isCancelable(now, cancelableDate)).isTrue();
    }

    @Test
    public void shouldNotBeCancelableWithDateInMinutePrecision() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cancelableDate = now.plusHours(BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS).minusMinutes(10);
        assertThat(BookingDelegateService.isCancelable(now, cancelableDate)).isFalse();
    }
}
