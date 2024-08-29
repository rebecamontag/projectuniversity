package com.rebecamontag.projectuniversity.repository;

import com.rebecamontag.projectuniversity.model.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Integer> {

    Optional<ClassRoom> findByRoomNumber(Integer roomNumber);


}
