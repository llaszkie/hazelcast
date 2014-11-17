/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Business entity.
 * 
 * @author LLASZKIE
 *
 */
public class Order implements Serializable {
	
	private static final long serialVersionUID = 8856160418686007699L;

	private long id;
	private String payload;
	private int total;
	
	/**
	 * C'tor
	 * 
	 * @param id
	 * @param payload
	 */
	public Order(long id, String payload, int total) {
		super();
		this.id = id;
		this.payload = payload;
		this.total = total;
	}

	/**
	 * some business logic here
	 */
	public void calculateTotal() {
		total = new Random(new Date().getTime()).nextInt(100000) + 1;
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException ignoreMe) {
			ignoreMe.printStackTrace();
		}
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + total;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

	
	
}
