package com.epam.training.ticketservice.data;

import com.epam.training.ticketservice.data.screenings.ScreeningService;
import com.epam.training.ticketservice.data.screenings.ScreeningServiceImpl;
import com.epam.training.ticketservice.data.screenings.model.ScreeningDto;
import com.epam.training.ticketservice.data.screenings.persistence.entity.Screening;
import com.epam.training.ticketservice.data.screenings.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        when(screeningRepository.findByMovieNameRoomNameAndScreeningDate(any(), any(), any()))
                .thenReturn(Optional.of(testScreening));

        underTest.delete(testScreening.getMovieName(), testScreening.getRoomName(), testScreening.getScreeningDate());

        //then
        Mockito.verify(screeningRepository)
                .findByMovieNameRoomNameAndScreeningDate(testScreening.getMovieName(),
                        testScreening.getRoomName(), testScreening.getScreeningDate());
        Mockito.verify(screeningRepository).delete(testScreening);
    }

    @Test
    public void testScreeningListAllShouldBeSuccessful() {
        //given
        Date screeningDate = null, screeningDate2 = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
            screeningDate2 = sf.parse("1997-02-08 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);
        List<Screening> testList = List.of(testScreening,
                new Screening(null, "test2", "testRoom", screeningDate2));

        //when
        when(screeningRepository.findAll()).thenReturn(testList);

        Optional<List<ScreeningDto>> resultList = underTest.listAll();

        //then
        Mockito.verify(screeningRepository).findAll();
        Assertions.assertTrue(resultList.isPresent());
        Assertions.assertEquals(2, resultList.get().size());
        Assertions.assertEquals(resultList.get().get(0).getMovieName(), testScreening.getMovieName());
        Assertions.assertEquals(resultList.get().get(1).getMovieName(), testList.get(1).getMovieName());
        Assertions.assertEquals(resultList.get().get(0).getRoomName(), testScreening.getRoomName());
        Assertions.assertEquals(resultList.get().get(1).getScreeningDate(), testList.get(1).getScreeningDate());

        Assertions.assertEquals(resultList.get().get(0),
                new ScreeningDto(testScreening.getMovieName(), testScreening.getRoomName(),
                        testScreening.getScreeningDate()));
    }

    @Test
    public void testScreeningListAllShouldReturnOptionalEmpty() {
        //given
        Date screeningDate = null, screeningDate2 = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
            screeningDate2 = sf.parse("1997-02-08 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);
        List<Screening> testList = List.of(testScreening,
                new Screening(null, "test2", "testRoom", screeningDate2));

        //when
        when(screeningRepository.findAll()).thenReturn(List.of());

        Optional<List<ScreeningDto>> resultList = underTest.listAll();

        //then
        Mockito.verify(screeningRepository).findAll();
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    public void testScreeningGetSpecificScreeningShouldReturnScreening() {
        //given
        Date screeningDate = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);

        //when
        when(screeningRepository
                .findByMovienameAndRoomname(testScreening.getMovieName(), testScreening.getRoomName()))
                .thenReturn(Optional.of(testScreening));

        Screening result = underTest.
                getSpecificScreening(testScreening.getMovieName(), testScreening.getRoomName());

        //then
        Mockito.verify(screeningRepository)
                .findByMovienameAndRoomname(testScreening.getMovieName(), testScreening.getRoomName());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, testScreening);
        Assertions.assertEquals(result.getMovieName(), testScreening.getMovieName());
    }

    @Test
    public void testScreeningGetSpecificScreeningShouldReturnNull() {
        //given
        Date screeningDate = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);

        //when
        when(screeningRepository
                .findByMovienameAndRoomname(testScreening.getMovieName(), testScreening.getRoomName()))
                .thenReturn(Optional.empty());

        Screening result = underTest.
                getSpecificScreening(testScreening.getMovieName(), testScreening.getRoomName());

        //then
        Mockito.verify(screeningRepository)
                .findByMovienameAndRoomname(testScreening.getMovieName(), testScreening.getRoomName());
        Assertions.assertNull(result);
    }

    @Test
    public void testScreeningGetSpecificScreeningByRoomShouldReturnListOfScreenings() {
        //given
        Date screeningDate = null, screeningDate2 = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
            screeningDate2 = sf.parse("1997-02-08 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);
        List<Screening> testList = List.of(testScreening,
                new Screening(null, "test2", "testRoom", screeningDate2));

        //when
        when(screeningRepository
                .findByRoomName(any()))
                .thenReturn(Optional.of(testList));

        List<Screening> result = underTest.
                getSpecificScreeningByRoom(testScreening.getRoomName());

        //then
        Mockito.verify(screeningRepository)
                .findByRoomName(testScreening.getRoomName());
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(result.get(0).getMovieName(), testScreening.getMovieName());
    }

    @Test
    public void testScreeningGetSpecificScreeningByRoomShouldReturnNull() {
        //given
        Date screeningDate = null;
        try {
            screeningDate = sf.parse("1997-02-07 08:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Screening testScreening = new Screening(null, "test", "testRoom", screeningDate);


        //when
        when(screeningRepository
                .findByRoomName(any()))
                .thenReturn(Optional.empty());

        List<Screening> result = underTest.
                getSpecificScreeningByRoom(testScreening.getRoomName());

        //then
        Mockito.verify(screeningRepository)
                .findByRoomName(testScreening.getRoomName());
        Assertions.assertNull(result);
    }

}
