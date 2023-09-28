package org.ionatomics.darkmatter.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.*;
import static cfscript.library.StdLib.*;
import io.quarkus.logging.Log;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.*;
import java.lang.*;
import java.util.*;
@Entity
public class Todo  extends PanacheEntity {
    /* No id is needed because we extend panacheentity */
    String description;
    String status;  

}