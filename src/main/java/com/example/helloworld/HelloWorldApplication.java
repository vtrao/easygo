package com.example.helloworld;

import com.easygo.daolayer.DAOFacade;
import com.easygo.model.Branch;
import com.easygo.model.Car;
import com.easygo.model.Customer;
import com.easygo.resource.BookingResource;
import com.easygo.resource.BranchResource;
import com.easygo.resource.CarResource;
import com.easygo.resource.CustomerResource;
import com.easygo.resource.SaleResource;
import com.easygo.resource.TripResource;
import com.example.helloworld.auth.ExampleAuthenticator;
import com.example.helloworld.auth.ExampleAuthorizer;
import com.example.helloworld.cli.RenderCommand;
import com.example.helloworld.core.Template;
import com.example.helloworld.core.User;
import com.example.helloworld.dao.DAOHandler;
import com.example.helloworld.filter.DateRequiredFeature;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.CategoriesResource;
import com.example.helloworld.resources.FilteredResource;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.resources.ProtectedResource;
import com.example.helloworld.resources.ViewResource;
import com.example.helloworld.tasks.EchoTask;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }


    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new SwaggerBundle<HelloWorldConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(HelloWorldConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(new ViewBundle<HelloWorldConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(HelloWorldConfiguration configuration) {
                return configuration.getViewRendererConfiguration();
            }
        });

    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
    	
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
            environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        
        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));
        environment.admin().addTask(new EchoTask());
        environment.jersey().register(DateRequiredFeature.class);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ExampleAuthenticator())
                .setAuthorizer(new ExampleAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //Register your resources
        try {
			environment.jersey().register(new CategoriesResource(DAOHandler.getInstance().getCategoriesDAO()));
			DAOFacade daoFacade = DAOFacade.getInstance();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ViewResource());
        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new FilteredResource());
        try {
			environment.jersey().register(new CarResource(DAOFacade.getInstance().getCarDAO()));
			environment.jersey().register(new BranchResource(DAOFacade.getInstance().getBranchDAO()));
			environment.jersey().register(new CustomerResource(DAOFacade.getInstance().getCustomerDAO()));
			environment.jersey().register(new BookingResource(DAOFacade.getInstance().getBookingDAO()));
			environment.jersey().register(new SaleResource(DAOFacade.getInstance().getSaleDAO()));
			environment.jersey().register(new TripResource(DAOFacade.getInstance().getTripDAO()));
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		System.out.println("Verify gets ");
		try {
			Branch tbr = DAOFacade.getInstance().getBranchDAO().getBranch(1L);
			Car tcar = DAOFacade.getInstance().getCarDAO().getCar(1L);
			Customer tcustomer = DAOFacade.getInstance().getCustomerDAO().getCustomer(1L);
			List<Car> allCars = DAOFacade.getInstance().getCarDAO().getCars(-1L,false);
			List<Car> hb1Cars = DAOFacade.getInstance().getCarDAO().getCars(1L,true);
			List<Car> cb4Cars = DAOFacade.getInstance().getCarDAO().getCars(4L,false);
			List<Customer> allCustomers = DAOFacade.getInstance().getCustomerDAO().getCustomer();
			List<Branch> allBranches = DAOFacade.getInstance().getBranchDAO().getBranch();
			System.out.println("Verify gets ");
			DAOFacade.getInstance().getBranchDAO().removeBranch(2L);
			DAOFacade.getInstance().getCarDAO().removeCar(3L);
			DAOFacade.getInstance().getCustomerDAO().removeCustomer(2L);
			List<Car> allCars2 = DAOFacade.getInstance().getCarDAO().getCars(-1L,false);
			List<Customer> allCustomers2 = DAOFacade.getInstance().getCustomerDAO().getCustomer();
			List<Branch> allBranches2 = DAOFacade.getInstance().getBranchDAO().getBranch();
			System.out.println("Verify gets ");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
