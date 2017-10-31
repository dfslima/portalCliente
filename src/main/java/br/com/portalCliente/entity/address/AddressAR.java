package br.com.portalCliente.entity.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public abstract class AddressAR {

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Address().entityManager;
		if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null){
			this.entityManager = entityManager();
		}
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null) {
			this.entityManager = entityManager();
		}
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Address attached = (Address) this;
			attached = find(attached.getId());
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null){
			this.entityManager = entityManager();
		}
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null){
			this.entityManager = entityManager();
		}
		this.entityManager.clear();
	}

	@Transactional
	public Address merge() {
		if (this.entityManager == null){
			this.entityManager = entityManager();
		}
		Address merged = (Address) this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static List<Address> findAll() {
		return entityManager().createQuery("SELECT o FROM Address o", Address.class).getResultList();
	}

	public static Address find(int id) {
		return entityManager().find(Address.class, id);
	}

}
