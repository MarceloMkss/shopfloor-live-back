package com.infotek.shopfloor_live.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "machine")
public class Machine implements Serializable {
	
	private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String name;

	    @NotBlank
	    private String vendor;

	    @NotBlank
	    private String model;

	    @NotBlank
	    private String status; // e.g. ACTIVE, INACTIVE

		public Machine() {
			super();
		}
		
		public Machine(Long id, String name, String vendor, String model, String status) {
			super();
			this.id = id;
			this.name = name;
			this.vendor = vendor;
			this.model = model;
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVendor() {
			return vendor;
		}

		public void setVendor(String vendor) {
			this.vendor = vendor;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, model, name, status, vendor);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Machine other = (Machine) obj;
			return Objects.equals(id, other.id) && Objects.equals(model, other.model)
					&& Objects.equals(name, other.name) && Objects.equals(status, other.status)
					&& Objects.equals(vendor, other.vendor);
		}

		@Override
		public String toString() {
			return "Machine [id=" + id + ", name=" + name + ", vendor=" + vendor + ", model=" + model + ", status="
					+ status + "]";
		}
	    
	    

}
