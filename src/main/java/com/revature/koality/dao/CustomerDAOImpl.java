package com.revature.koality.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.revature.koality.bean.Album;
import com.revature.koality.bean.Customer;
import com.revature.koality.bean.CustomerCredentials;
import com.revature.koality.bean.CustomerDetail;
import com.revature.koality.bean.Image;
import com.revature.koality.bean.Publisher;
import com.revature.koality.bean.Track;
import com.revature.koality.utility.HibernateUtility;

public class CustomerDAOImpl implements CustomerDAO {

	private SessionFactory sessionFactory;

	public CustomerDAOImpl() {
		this.sessionFactory = HibernateUtility.getMainSessionFactory();
	}

	public CustomerDAOImpl(boolean isTest) {
		if (isTest) {
			this.sessionFactory = HibernateUtility.getTestSessionFactory();
		} else {
			this.sessionFactory = HibernateUtility.getMainSessionFactory();
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int addCustomer(Customer customer) {

		int id = -1;
		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				id = (int) session.save(customer);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				id = -1;
			} finally {
				session.close();
			}
		}

		return id;

	}

	@Override
	public CustomerCredentials getCustomerCredentialsByUsername(String username) {

		CustomerCredentials customerCredentials = null;
		Session session = null;

		if (this.sessionFactory != null) {
			String hql = "FROM CustomerCredentials cc WHERE cc.username = :username";
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				customerCredentials = session.createQuery(hql, CustomerCredentials.class)
						.setParameter("username", username).getSingleResult();
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return customerCredentials;

	}

	@Override
	public Customer getCustomerById(int customerId) {

		Customer customer = null;
		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				customer = session.get(Customer.class, customerId);
				customer.loadImageUrl();
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return customer;

	}

	@Override
	public List<Customer> getAllCustomers() {

		List<Customer> customerList = null;
		Session session = null;

		if (this.sessionFactory != null) {
			try {
				String hql = "FROM Customer c ORDER BY c.customerId ASC";
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				customerList = session.createQuery(hql, Customer.class).getResultList();
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return customerList;

	}

	@Override
	public boolean updateCustomerDetail(int customerId, CustomerDetail customerDetail) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				customer.setCustomerDetail(customerDetail);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return false;

	}

	@Override
	public boolean updateCustomerImage(int customerId, Image image) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				customer.getImage().setImageType(image.getImageType());
				customer.getImage().setImageData(image.getImageData());
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return false;

	}

	@Override
	public boolean updateCustomerCredentials(int customerId, CustomerCredentials customerCredentials) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				customer.getCustomerCredentials().setUsername(customerCredentials.getUsername());
				customer.getCustomerCredentials().setHashSalt(customerCredentials.getHashSalt());
				customer.getCustomerCredentials().setPasswordHash(customerCredentials.getPasswordHash());
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return false;

	}

	@Override
	public List<Publisher> getAllSubscribeeByCustomerId(int customerId) {

		List<Publisher> publisherList = null;
		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				publisherList = customer.getPublisherList();
				Hibernate.initialize(publisherList);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return publisherList;

	}

	@Override
	public List<Track> getAllTracksByCustomerId(int customerId) {

		List<Track> trackList = null;
		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				trackList = customer.getTrackList();
				Hibernate.initialize(trackList);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return trackList;

	}

	@Override
	public List<Album> getAllAlbumsByCustomerId(int customerId) {

		List<Album> albumList = null;
		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				albumList = customer.getAlbumList();
				Hibernate.initialize(albumList);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return albumList;

	}

	@Override
	public boolean subscribeToPublisher(int customerId, int publisherId) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				customer.getPublisherList().add(session.load(Publisher.class, publisherId));
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return false;

	}

	@Override
	public boolean unsubscribeFromPublisher(int customerId, int publisherId) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				customer.getPublisherList().removeIf(p -> p.getPublisherId() == publisherId);
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return false;

	}

	@Override
	public boolean purchaseTrack(int customerId, int trackId) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				customer.getTrackList().add(session.load(Track.class, trackId));
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally {
				session.close();
			}
		}

		return false;

	}

	@Override
	public boolean purchaseAlbum(int customerId, int albumId) {

		Session session = null;

		if (this.sessionFactory != null) {
			try {
				session = this.sessionFactory.getCurrentSession();
				session.beginTransaction();
				Customer customer = session.get(Customer.class, customerId);
				Album album = session.get(Album.class, albumId);
				customer.getAlbumList().add(album);

				List<Track> oldTrackList = customer.getTrackList();
				List<Track> newTrackList = album.getTrackList();
				for (Track t : newTrackList) {
					if (!oldTrackList.contains(t)) {
						oldTrackList.add(t);
					}
				}
				session.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			}
		}

		return false;

	}

}
