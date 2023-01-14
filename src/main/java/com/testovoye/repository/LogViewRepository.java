package com.testovoye.repository;

import com.testovoye.model.LogViewEntity;
import com.testovoye.repository.responce.AggrChartResult;
import com.testovoye.repository.responce.AggrTableResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogViewRepository extends PagingAndSortingRepository<LogViewEntity, Integer>, JpaSpecificationExecutor<LogViewEntity> {

    @Query(nativeQuery = true, value = "select site_id\n" +
            "from log_view\n" +
            "group by site_id;\n")
    List<String> preloadSiteIds();

    @Query(nativeQuery = true, value = "select mm_dma\n" +
            "from log_view\n" +
            "group by mm_dma;\n")
    List<Integer> preloadMMDmas();

    @Query(nativeQuery = true, value = "with event_counter_ctr as (select count(log_view.id) counter, site_id\n" +
            "                           from log_view\n" +
            "                                    join event_log el on log_view.uid = el.uid\n" +
            "                           where el.tag is not null\n" +
            "                             and el.tag not like 'v%'\n" +
            "                           group by log_view.site_id),\n" +
            "     overall_counter as (select count(log_view.id) counter, site_id\n" +
            "                         from log_view\n" +
            "                         group by log_view.site_id),\n" +
            "     event_counter_evpm as (select count(log_view.id) counter, site_id\n" +
            "                            from log_view\n" +
            "                                     join event_log el on log_view.uid = el.uid\n" +
            "                            where el.tag is not null\n" +
            "                            group by log_view.site_id)\n" +
            "select " +
            "overall_counter.site_id as siteId,\n" +
            "overall_counter.counter                                                    views,\n" +
            "       trunc(event_counter_ctr.counter * 100.0 / overall_counter.counter, 2)   as ctr,\n" +
            "       trunc(event_counter_evpm.counter * 1000.0 / overall_counter.counter, 2) as evpm\n" +
            "from overall_counter\n" +
            "         left join event_counter_ctr on event_counter_ctr.site_id = overall_counter.site_id\n" +
            "         join event_counter_evpm on event_counter_evpm.site_id = overall_counter.site_id\n",
            countQuery = "with event_counter_ctr as (select count(log_view.id) counter, site_id\n" +
                    "                           from log_view\n" +
                    "                                    join event_log el on log_view.uid = el.uid\n" +
                    "                           where el.tag is not null\n" +
                    "                             and el.tag not like 'v%'\n" +
                    "                           group by log_view.site_id),\n" +
                    "     overall_counter as (select count(log_view.id) counter, site_id\n" +
                    "                         from log_view\n" +
                    "                         group by log_view.site_id),\n" +
                    "     event_counter_evpm as (select count(log_view.id) counter, site_id\n" +
                    "                            from log_view\n" +
                    "                                     join event_log el on log_view.uid = el.uid\n" +
                    "                            where el.tag is not null\n" +
                    "                            group by log_view.site_id)\n" +
                    "select count(overall_counter.counter) counter\n" +
                    "from overall_counter\n" +
                    "         left join event_counter_ctr on event_counter_ctr.site_id = overall_counter.site_id\n" +
                    "         join event_counter_evpm on event_counter_evpm.site_id = overall_counter.site_id\n")
    Page<AggrTableResult> aggrTable(Pageable pageable);

    @Query(nativeQuery = true, value = "with sub_v as (select to_timestamp(floor((extract('epoch' from reg_time) / (60 * 60 * 3))) * (60 * 60 * 3)) as interval_alias_v\n" +
            "                    , count(event_log.uid)                                                                  as count_v\n" +
            "               from event_log\n" +
            "                        join log_view lv on event_log.uid = lv.uid\n" +
            "               where site_id = :siteId\n" +
            "                 and event_log.tag is not null\n" +
            "                 and event_log.tag not like 'v%'\n" +
            "               group by interval_alias_v\n" +
            "               order by interval_alias_v)\n" +
            "   , sub_no_v as (select to_timestamp(floor((extract('epoch' from reg_time) / (60 * 60 * 3))) * (60 * 60 * 3)) as interval_alias_v\n" +
            "                       , count(event_log.uid)                                                                  as count_v\n" +
            "                  from event_log\n" +
            "                           join log_view lv on event_log.uid = lv.uid\n" +
            "                  where site_id = :siteId\n" +
            "                  group by interval_alias_v\n" +
            "                  order by interval_alias_v)\n" +
            "   , sub as (select to_timestamp(floor((extract('epoch' from reg_time) / (60 * 60 * 3))) * (60 * 60 * 3))\n" +
            "    as interval_alias\n" +
            "                  , count(log_view.uid)\n" +
            "             from log_view\n" +
            "             where site_id = :siteId\n" +
            "             group by interval_alias\n" +
            "             order by interval_alias)\n" +
            "select sub.count                             as events\n" +
            "     , trunc(sub_v.count_v * 100.0 / sub.count,2)     as ctr\n" +
            "     , trunc(sub_no_v.count_v * 1000.0 / sub.count,2) as evpm\n" +
            "     , sub.interval_alias as intervalAlias\n" +
            "from sub\n" +
            "         left join sub_v on sub.interval_alias = sub_v.interval_alias_v\n" +
            "         left join sub_no_v on sub.interval_alias = sub_no_v.interval_alias_v")
    List<AggrChartResult> aggrChart(@Param("siteId") String siteId);

}
