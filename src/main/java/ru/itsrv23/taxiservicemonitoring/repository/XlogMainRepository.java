package ru.itsrv23.taxiservicemonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itsrv23.taxiservicemonitoring.entity.XlogMain;

import java.time.OffsetDateTime;

@Repository
public interface XlogMainRepository extends JpaRepository<XlogMain, Integer> {

    @Query(value = "select count(idx) from xlog_main where editdate >= ? and descr like %?%", nativeQuery = true)
    Long selectCountError(OffsetDateTime date, String descr);
}
