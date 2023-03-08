package com.tp2.pry20220271.ulcernosis.services;

import com.tp2.pry20220271.ulcernosis.exceptions.NotFoundException;
import com.tp2.pry20220271.ulcernosis.models.entities.Nurse;
import com.tp2.pry20220271.ulcernosis.models.entities.Schedule;
import com.tp2.pry20220271.ulcernosis.models.repositories.NurseRepository;
import com.tp2.pry20220271.ulcernosis.models.repositories.ScheduleRepository;
import com.tp2.pry20220271.ulcernosis.models.services.ScheduleService;
import com.tp2.pry20220271.ulcernosis.resources.request.SaveScheduleResourceTimeIn;
import com.tp2.pry20220271.ulcernosis.resources.response.ScheduleResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final ScheduleRepository scheduleRepository;
    private final NurseRepository nurseRepository;



    @Override
    public ScheduleResource saveTimeIn(SaveScheduleResourceTimeIn schedule) {
        Nurse nurse = nurseRepository.findById(schedule.getNurseId()).orElseThrow(() -> new NotFoundException("Nurse","Id",schedule.getNurseId()));
        Schedule newSchedule = new Schedule();
        newSchedule.setNurse(nurse);
        newSchedule.setTimeIn(schedule.getTime());
        newSchedule.setAltitudeIn(schedule.getAltitude());
        newSchedule.setLatitudeIn(schedule.getLatitude());
        newSchedule.setLongitudeIn(schedule.getLongitude());

        return modelMapper.map(scheduleRepository.save(newSchedule), ScheduleResource.class);
    }

    @Override
    public ScheduleResource saveTimeOut(SaveScheduleResourceTimeIn schedule) {
        Nurse nurse = nurseRepository.findById(schedule.getNurseId()).orElseThrow(() -> new NotFoundException("Nurse","Id",schedule.getNurseId()));
        Schedule timeOutSchedule = scheduleRepository.findByNurseId(schedule.getNurseId()).orElseThrow(() -> new NotFoundException("Schedule","NurseId",schedule.getNurseId()));
        timeOutSchedule.setTimeOut(schedule.getTime());
        timeOutSchedule.setAltitudeOut(schedule.getAltitude());
        timeOutSchedule.setLatitudeOut(schedule.getLatitude());
        timeOutSchedule.setLongitudeOut(schedule.getLongitude());
        return modelMapper.map(scheduleRepository.save(timeOutSchedule), ScheduleResource.class);
    }

    @Override
    public List<ScheduleResource> findAllSchedulesByNurseId(Long nurseId) {
        List<Schedule> schedules = scheduleRepository.findAllByNurseId(nurseId);
        return schedules.stream().map(schedule -> modelMapper.map(schedule, ScheduleResource.class)).collect(Collectors.toList());
    }
}
