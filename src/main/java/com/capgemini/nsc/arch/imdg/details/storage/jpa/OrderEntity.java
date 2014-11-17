/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LLASZKIE
 *
 */
@Entity
@Table(name="Orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String payload;
	private Integer total;
	
	
	public OrderEntity(long id, String payload, Integer total) {
		super();
		this.id = id;
		this.payload = payload;
		this.total = total;
	}
	
	public OrderEntity() {
		super();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
