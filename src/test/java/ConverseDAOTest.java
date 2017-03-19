import org.junit.jupiter.api.Test;
import ru.innopolis.smoldyrev.common.exceptions.ConverseDaoException;
import ru.innopolis.smoldyrev.common.exceptions.UserDaoException;
import ru.innopolis.smoldyrev.models.dao.ConverseDAO;
import ru.innopolis.smoldyrev.models.dao.UserDAO;
import ru.innopolis.smoldyrev.models.dto.ConversationDTO;
import ru.innopolis.smoldyrev.models.dto.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by smoldyrev on 18.03.17.
 */
public class ConverseDAOTest {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    private ConverseDAO converseDAO = new ConverseDAO();

/*    @Test
    public void getUsersInConverseTest() {
        int converse = 15;
        EntityManager entityManager = FACTORY.createEntityManager();
        TypedQuery<ConversationDTO> query = entityManager.createQuery(
                "SELECT converse FROM ConversationDTO converse where converse.id = :id", ConversationDTO.class);
        ConversationDTO conversation = query.setParameter("id", converse).getSingleResult();
        System.out.println("//////////" + conversation.getUsers().size());
        assertNotNull(conversation);
        assertNotNull(conversation.getUsers());
        assertTrue(conversation.getUsers().size() > 0);
    }


    @Test
    public void getEntityByIdTest() {
        int converse = 15;
        ConversationDTO conversationDTO = converseDAO.getEntityById(converse);
        System.out.println(conversationDTO.getId());
        System.out.println(conversationDTO.getUsers().size());
        System.out.println(conversationDTO.getUsers());
        assertNotNull(conversationDTO);
        assertNotNull(conversationDTO.getUsers());
        assertTrue(conversationDTO.getUsers().size() > 0);
    }*/

    @Test
    public void addConverseMemberTest() throws ConverseDaoException {

        int userId = 5;
        int converse = 15;

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            UserDAO udao = new UserDAO();
            ConverseDAO cdao = new ConverseDAO();

            UserDTO u = udao.getEntityById(userId);


            ConversationDTO conversationDTO = cdao.getEntityById(converse);
            System.out.println(conversationDTO.getUsers().size());
            conversationDTO.addUser(u);
            System.out.println(conversationDTO.getUsers().size());
            conversationDTO = entityManager.merge(conversationDTO);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (UserDaoException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void addConverseMember2Test() throws ConverseDaoException {
//
//        int userId = 68;
//        int converse = 15;
//
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            ConversationDTO conversationDTO = (ConversationDTO)session.get(ConversationDTO.class, converse);
//            UserDTO userDTO = (UserDTO)session.get(UserDTO.class, userId);
//            conversationDTO.getUsers().add(userDTO);
//            session.save(conversationDTO);
//            transaction.commit();
//        } catch( Exception e ) {
//            transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }


}
