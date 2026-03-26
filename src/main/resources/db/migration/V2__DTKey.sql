ALTER TABLE helmet_events
    DROP CONSTRAINT fk_helmet_events_on_metric_date_time;

ALTER TABLE helmet_events
    ADD CONSTRAINT FK_HELMET_EVENTS_ON_DATE_TIME FOREIGN KEY (date_time) REFERENCES metrics (date_time);

ALTER TABLE helmet_events
    DROP COLUMN metric_date_time;