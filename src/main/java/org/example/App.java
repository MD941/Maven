package org.example;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class App
{
    public static void main( String[] args )
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        String hqlAuthorInsert = "insert into Author (name) values (:authorName)";
        Query queryAuthor = session.createQuery(hqlAuthorInsert);
        queryAuthor.setParameter("name", "Author 1");
        int authorResult = queryAuthor.executeUpdate();
        System.out.println( authorResult);

        transaction.commit();
    }
}
