package br.com.fio.cepp.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	//private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
	private static SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();

	public static SessionFactory getFabricaDeSessoes() {
		return fabricaDeSessoes;
	}

	public static Connection getConexao() {
		Session sessao = fabricaDeSessoes.openSession();

		Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection connection) throws SQLException {
				return connection;
			}
		});

		return conexao;
	}

	private static SessionFactory criarFabricaDeSessoes() {
		try {
			Configuration configuracao = new Configuration().configure();

			ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties())
					.build();

			SessionFactory fabrica = configuracao.buildSessionFactory(registro);

			return fabrica;
		} catch (Throwable ex) {
			//logger.error("A fábrica de sessões não pode ser criada: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
