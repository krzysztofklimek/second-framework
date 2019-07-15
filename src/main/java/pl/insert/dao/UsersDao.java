//https://www.baeldung.com/hibernate-entitymanager
//https://www.youtube.com/watch?v=Yv2xctJxE-w&list=PL4AFF701184976B25&index=1

package pl.insert.dao;


import pl.insert.hibernate.TransactionCallback;
import pl.insert.hibernate.TransactionTemplate;
import pl.insert.model.User;


import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class UsersDao {



    public List<?> getEmployeeList() throws InvocationTargetException, IllegalAccessException {

        return TransactionTemplate.execute(new TransactionCallback(){
           public Object doInTransaction(EntityManager entityManager){
               List<?> users = entityManager.createQuery("SELECT user FROM User user").getResultList();
               return users;
           }
        });

    }


    public User getUserById(Long userId) throws InvocationTargetException, IllegalAccessException {

        return TransactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(EntityManager entityManager) {
                User user =entityManager.find(User.class, new Long(userId));
                return user;
            }
        });

    }



    public User insertUser(User user) throws InvocationTargetException, IllegalAccessException {
        //TransactionTemplate.execute(session -> session.save(emp));

        return TransactionTemplate.execute(new TransactionCallback(){
            public User doInTransaction(EntityManager entityManager){
                entityManager.persist(user);
                return user;
            }
        });

    }



    public User deleteUser(User user) throws InvocationTargetException, IllegalAccessException {
        //TransactionTemplate.execute(session -> session.save(emp));

        return TransactionTemplate.execute(new TransactionCallback(){
            public User doInTransaction(EntityManager entityManager){
                entityManager.remove(
                        entityManager.contains(user) ? user : entityManager.merge(user)
                );
                return user;
            }
        });

    }




    public static void main(String[] a) throws InvocationTargetException, IllegalAccessException {

        UsersDao userDao = new UsersDao();

        User user = new User();
        user.setId(4);
        user.setName("Babu");
        user.setSurname("Security");



        //System.out.println(empDao.insertUser(user));
        //System.out.println(userDao.deleteUser(user));
        //System.out.println(userDao.getUserById((long) 12));
        System.out.println(userDao.getEmployeeList());

    }
}
