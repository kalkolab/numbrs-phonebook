package me.kalko.phonebook;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.kalko.phonebook.domain.User;
import me.kalko.phonebook.domain.dao.ContactService;
import me.kalko.phonebook.domain.dao.UserService;
import me.kalko.phonebook.resources.ContactResource;
import me.kalko.phonebook.resources.UserResource;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

/**
 * Hello world!
 *
 */
public class PhonebookApp extends Application<PhonebookConfiguration> {

    public static void main(String[] args) throws Exception {
        new PhonebookApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }

    public void run(PhonebookConfiguration configuration, Environment environment) throws Exception {
        final DataSource dataSource =
                configuration.getDataSourceFactory().build(environment.metrics(), "sql");
        DBI dbi = new DBI(dataSource);

        AuthFilter jwtAuthFilter = new JwtAuthFilter(dbi.onDemand(UserService.class));

        environment.jersey().register(new AuthDynamicFeature(jwtAuthFilter));
        environment.jersey().register(new AuthValueFactoryProvider.Binder(User.class));

        environment.jersey().register(new UserResource(dbi.onDemand(UserService.class)));
        environment.jersey().register(new ContactResource(dbi.onDemand(ContactService.class)));
    }
}
