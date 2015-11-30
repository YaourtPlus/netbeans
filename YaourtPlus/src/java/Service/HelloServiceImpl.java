/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.*;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class HelloServiceImpl implements HelloService {

	@Resource
	HelloDAO dao;

	@Override
	public void add(String nom, String message) {
		dao.save(new HelloEntity(nom, message));
	}

	@Override
	public void remove(String nom) {
		List<HelloEntity> entities = dao.findByName(nom);
		for (int i = 0; i < entities.size(); i++) {
			dao.delete(entities.get(i));
		}
	}

	@Override
	public String getNomsMessages() {
		String result = "";
		List<HelloEntity> entities = dao.findAll();
		for (int i = 0; i < entities.size(); i++) {
			result += entities.get(i).getMessage() + " " + entities.get(i).getNom();
		}
		return result;
	}
}