package com.easygo.daolayer;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.easygo.daolayer.impl.BookingDAO;
import com.easygo.daolayer.impl.BranchDAO;
import com.easygo.daolayer.impl.CarDAO;
import com.easygo.daolayer.impl.CustomerDAO;
import com.easygo.daolayer.impl.SaleDAO;
import com.easygo.daolayer.impl.TripDAO;
import com.easygo.model.Branch;
import com.easygo.model.Car;
import com.easygo.model.Car.CarTypeEnum;
import com.easygo.model.Car.CarStatusEnum;
import com.easygo.model.Customer;
import com.easygo.model.Customer.CustomerTypeEnum;
import com.easygo.system.db.pool.JDBCConnectionPool;
import com.easygo.system.db.pool.ObjectPool;

public class DAOFacade {
	private static DAOFacade sInstance = null;

	public static DateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
	private ObjectPool<Connection> connectionPool = null;
	private ICarDAO mCarDAO = null;
	private IBranchDAO mBranchDAO = null;
	private ICustomerDAO mCustomerDAO = null;
	private IBookingDAO mBookingDAO = null;
	private ISaleDAO mSaleDAO = null;
	private ITripDAO mTripDAO = null;
	private IUserDAO mUserDAO = null;
	
	/**
	 * Using a getInstance() method instead of a public constructor to only
	 * allow one instance of this object across the application.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static DAOFacade getInstance() throws Exception {
		if (sInstance == null) {
			synchronized (DAOFacade.class) {
				if (sInstance == null) {
					sInstance = new DAOFacade();
				}
			}
		}
		return sInstance;
	}

	public ICarDAO getCarDAO() {
		return mCarDAO;
	}

	public IBranchDAO getBranchDAO() {
		return mBranchDAO;
	}

	public ICustomerDAO getCustomerDAO() {
		return mCustomerDAO;
	}

	public IBookingDAO getBookingDAO() {
		return mBookingDAO;
	}

	public ISaleDAO getSaleDAO() {
		return mSaleDAO;
	}
	
	public ITripDAO getTripDAO() {
		return mTripDAO;
	}
	
	public IUserDAO getUserDAO() {
		return mUserDAO;
	}

	private DAOFacade() throws Exception {
		connectionPool = new JDBCConnectionPool(
				// "jdbc:sqlite:file:memdb1?mode=memory&cache=shared"
				/* &read_uncommitted=ON */
				///*inmemory=> */"jdbc:sqlite:file::memory:?cache=shared&foreign_keys=ON", null, null, true);
				"jdbc:sqlite:file::test.db:?cache=shared&foreign_keys=ON", null, null, true);
		try {
			createStorageStructure();
		} catch (Exception e) {
			System.out.println("InMemoryTable creation exception: " + e);
		}
		setAllTableManagers();
		try {
			loadInitialData();
		} catch (Exception e) {
			System.out.println("Inital Data load exception: " + e);
		}
	}

	// Creates tables in InMemory db, reading table definitions from
	// dtftables.xml
	private void createStorageStructure() throws Exception {
		Connection inMemoryDbConnection = null;
		Statement stmt = null;
		try {
			inMemoryDbConnection = connectionPool.checkOut();
			stmt = inMemoryDbConnection.createStatement();
			// File fXmlFile = new File();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(DAOFacade.class.getClassLoader().getResourceAsStream("schemaeasygo.xml"));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("inmemory");
			System.out.println("----------------------------");
			// TODO: Refactor these loops and decouple default from hardcoding
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Element inmemory = (Element) nList.item(temp);
				NodeList dbObjects = inmemory.getElementsByTagName("dbobject");
				for (int i = 0; i < dbObjects.getLength(); ++i) {
					Element dbObject = (Element) dbObjects.item(i);
					System.out.println(dbObject.getAttribute("type") + " id : " + dbObject.getAttribute("id")
							+ " name: " + dbObject.getAttribute("name"));
					System.out.println("Stmt : " + dbObject.getTextContent());
					stmt.executeUpdate(dbObject.getTextContent());
				}
			}
			if (!connectionPool.isAutoCommit()) {
				inMemoryDbConnection.commit();
			}
			System.out.println("----------TABLES CREATED------------------");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			connectionPool.checkIn(inMemoryDbConnection);
		}

	}

	private void loadInitialData() throws Exception {
		// Add Customers
		Customer cm = new Customer();
		cm.setId(1L);
		cm.setName("Venkat");
		cm.setType(CustomerTypeEnum.INDIVIDUAL);
		mCustomerDAO.addCustomer(cm);
		cm.setId(2L);
		cm.setName("Saurish");
		cm.setType(CustomerTypeEnum.PREMIUM);
		mCustomerDAO.addCustomer(cm);
		cm.setId(3L);
		cm.setName("Rishaank");
		cm.setType(CustomerTypeEnum.PREMIUM);
		mCustomerDAO.addCustomer(cm);
		cm.setId(4L);
		cm.setName("IntuitBglr");
		cm.setType(CustomerTypeEnum.CORPORATE);
		mCustomerDAO.addCustomer(cm);
		cm.setId(5L);
		cm.setName("IntuitHyd");
		cm.setType(CustomerTypeEnum.CORPORATE);
		mCustomerDAO.addCustomer(cm);

		String[] carName = { "Toyoto Etios", "Maruti Swift", "Renault Duster" };
		float[] carCost = { 25.0f, 10.5f, 50.0f };
		CarTypeEnum[] carType = { CarTypeEnum.SEDAN, CarTypeEnum.COMPACT, CarTypeEnum.SUV };
		Car car = new Car();
		long carId = 1L;
		Random random = new Random(1);
		int max = carType.length - 1, min = 0;

		// Add Branches and cars to the branches
		Branch br = new Branch();
		br.setId(1L);
		br.setName("Tarnaka, Hyderabad");
		br.setMin(8);
		br.setMax(15);
		mBranchDAO.addBranch(br);
		for (; carId <= 10L; ++carId) {
			// random.nextInt(max - min + 1) + min
			int x = random.nextInt(max - min + 1) + min;
			car.setId(carId);
			car.setName(carName[x]);
			car.setReg("AP" + carId + "A" + (1000 + carId));
			car.setType(carType[x]);
			car.setStatus(CarStatusEnum.AVAILABLE);
			car.setHomebranch(br);
			car.setCurrentbranch(br);
			car.setCostPerDay(carCost[x]);
			mCarDAO.addCar(car);
		}

		br.setId(2L);
		br.setName("Gachibowli, Hyderabad");
		br.setMin(5);
		br.setMax(15);
		mBranchDAO.addBranch(br);
		for (; carId <= 30; ++carId) {
			// random.nextInt(max - min + 1) + min
			int x = random.nextInt(max - min + 1) + min;
			car.setId(carId);
			car.setName(carName[x]);
			car.setReg("AP" + carId + "A" + (2000 + carId));
			car.setType(carType[x]);
			car.setStatus(CarStatusEnum.AVAILABLE);
			car.setHomebranch(br);
			car.setCurrentbranch(br);
			car.setCostPerDay(carCost[x]);
			mCarDAO.addCar(car);
		}

		br.setId(3L);
		br.setName("Sarjapur, Bangalore");
		br.setMin(10);
		br.setMax(20);
		mBranchDAO.addBranch(br);
		for (; carId <= 50; ++carId) {
			// random.nextInt(max - min + 1) + min
			int x = random.nextInt(max - min + 1) + min;
			car.setId(carId);
			car.setName(carName[x]);
			car.setReg("KA" + carId + "A" + (1000 + carId));
			car.setType(carType[x]);
			car.setStatus(CarStatusEnum.AVAILABLE);
			car.setHomebranch(br);
			car.setCurrentbranch(br);
			car.setCostPerDay(carCost[x]);
			mCarDAO.addCar(car);
		}

		br.setId(4L);
		br.setName("Yelhanka, Bangalore");
		br.setMin(10);
		br.setMax(20);
		mBranchDAO.addBranch(br);
		for (; carId <= 70; ++carId) {
			// random.nextInt(max - min + 1) + min
			int x = random.nextInt(max - min + 1) + min;
			car.setId(carId);
			car.setName(carName[x]);
			car.setReg("KA" + carId + "A" + (2000 + carId));
			car.setType(carType[x]);
			car.setStatus(CarStatusEnum.AVAILABLE);
			car.setHomebranch(br);
			car.setCurrentbranch(br);
			car.setCostPerDay(carCost[x]);
			mCarDAO.addCar(car);
		}
	}

	private void setAllTableManagers() throws Exception {
		// mCategoriesTableHandler = new
		// CategoriesDAOAdjacencyList(connectionPool);
		mCarDAO = new CarDAO(connectionPool);
		mBranchDAO = new BranchDAO(connectionPool);
		mCustomerDAO = new CustomerDAO(connectionPool);
		mBookingDAO = new BookingDAO(connectionPool);
		mTripDAO = new TripDAO(connectionPool);
		mSaleDAO = new SaleDAO(connectionPool);
	}

	public static void main(String args[]) {
		Random random = new Random();
		// random.nextInt(max - min + 1) + min
		for (int i = 0; i < 100; ++i)
			System.out.println(random.nextInt(3 - 1 + 1) + 1);
	}
}
