@Entity
component name="Todo" extends="PanacheEntity" {
    /* No id is needed because we extend panacheentity */
    property name="description" type="String" hint="The description of the todo item.";
    property name="status" type="String" hint="The status of the todo item";  

}