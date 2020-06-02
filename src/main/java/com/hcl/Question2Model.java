package com.hcl;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "csv_data")
public class Question2Model{

	/*
	 *  CREATE TABLE public.csv_data
		(
		  id bigint NOT NULL,
		  granularity_period bigint,
		  call_attempts bigint,
		  cell_id bigint,
		  object_name character varying(255),
		  CONSTRAINT csv_data_pkey PRIMARY KEY (id)
		)
		WITH (
		  OIDS=FALSE
		);
	 * Sequence used
	 * CREATE SEQUENCE public."hcl_seq"
	    INCREMENT 1
	    START 1
	    MINVALUE 1
	    MAXVALUE 9223372036854775807
	    CACHE 1;
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objectid_generator")
	@SequenceGenerator(name = "objectid_generator", sequenceName = "hcl_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	private LocalDateTime resultTime;
	private Long GranularityPeriod;
	private String objectName;
	private Long cellId;
	private Long callAttempts;
	
	public Long getGranularityPeriod() {
		return GranularityPeriod;
	}
	public void setGranularityPeriod(Long granularityPeriod) {
		GranularityPeriod = granularityPeriod;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Long getCellId() {
		return cellId;
	}
	public void setCellId(Long cellId) {
		this.cellId = cellId;
	}
	public Long getCallAttempts() {
		return callAttempts;
	}
	public void setCallAttempts(Long callAttempts) {
		this.callAttempts = callAttempts;
	}
	public LocalDateTime getResultTime() {
		return resultTime;
	}
	public void setResultTime(LocalDateTime resultTime) {
		this.resultTime = resultTime;
	}
	
}
