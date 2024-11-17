package org.group4.module.manager;

import org.group4.dao.FactoryDAO;
import org.group4.dao.MemberDAO;
import org.group4.module.users.Member;

public class MemberManager implements GenericManager<Member> {

  private static final MemberDAO memberDAO = FactoryDAO.getMemberDAO();

  @Override
  public boolean add(Member member) {
    return memberDAO.add(member);
  }

  @Override
  public boolean update(Member member) {
    return memberDAO.update(member);
  }

  @Override
  public boolean delete(String id) {
    return memberDAO.delete(id);
  }

}
