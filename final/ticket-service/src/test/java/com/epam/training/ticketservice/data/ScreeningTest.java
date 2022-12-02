package com.epam.training.ticketservice.data;

import com.epam.training.ticketservice.data.screenings.ScreeningService;
import com.epam.training.ticketservice.data.screenings.ScreeningServiceImpl;
import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import com.epam.training.ticketservice.data.screenings.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScreeningTest {


    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final ScreeningService underTest = new ScreeningServiceImpl(screeningRepository);
    private final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Test
    public void testScreeningCreateShouldBeSuccessful() {
        //given
        Date screeningDate = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);

        //when
        when(screeningRepository.save(any())).thenReturn(testScreening);

        underTest.create(testScreening.getMovieName(), testScreening.getRoomName(), testScreening.getScreeningDate());

        //then
        Mockito.verify(screeningRepository).save(testScreening);

    }

    @Test
    public void testScreeningDeleteShouldBeSuccessful() {
        //given
        Date screeningDate = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);

        //when
        doNothing().when(screeningRepository).delete(any());
        when(screeningRepository.findByMovieMovieNameRoomRoomNameAndScreeningDate(any(), any(), any()))
                .thenReturn(Optional.of(testScreening));

        underTest.delete(testScreening.getMovieName(), testScreening.getRoomName(), testScreening.getScreeningDate());

        //then
        Mockito.verify(screeningRepository)
                .findByMovieMovieNameRoomRoomNameAndScreeningDate(testScreening.getMovieName(),
                        testScreening.getRoomName(), testScreening.getScreeningDate());
        Mockito.verify(screeningRepository).delete(testScreening);

    }


}
