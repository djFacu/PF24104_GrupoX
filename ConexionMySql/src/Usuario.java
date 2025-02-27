public class Usuario {

   private int id;
   private String nombre;
   private String clave;

   
   public Usuario() {
   }


   public Usuario(int id, String nombre, String clave) {
      this.id = id;
      this.nombre = nombre;
      this.clave = clave;
   }


   public int getId() {
      return id;
   }


   public void setId(int id) {
      this.id = id;
   }


   public String getNombre() {
      return nombre;
   }


   public void setNombre(String nombre) {
      this.nombre = nombre;
   }


   public String getClave() {
      return clave;
   }


   public void setClave(String clave) {
      this.clave = clave;
   }


   @Override
   public String toString() {
      return "Usuario [id=" + id + ", nombre=" + nombre + ", clave=" + clave + "]";
   }

}
