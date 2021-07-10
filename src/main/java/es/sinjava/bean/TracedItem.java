package es.sinjava.bean;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "trace")
@Data
public class TracedItem {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String host;
	
	@Column(nullable = false)
	private String clsName;

	@Column(nullable = false)
	private String methodName;

	@Column(nullable = false)
	private long lap;
	
	@Column(nullable = false)
	private LocalDateTime created ;
	
}
