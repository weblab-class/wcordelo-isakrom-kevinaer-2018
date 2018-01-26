package io.smartraise.service.impl;

import io.smartraise.dao.ContactInformationDAO;
import io.smartraise.dao.MemberDAO;
import io.smartraise.util.Parser;
import io.smartraise.model.accounts.Member;
import io.smartraise.model.fundraise.Organization;
import io.smartraise.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private ContactInformationDAO contactInformationDAO;

    @Override
    public Member get(String id) {
        if (Parser.isEmail(id)) {
            return memberDAO.findByEmail(id);
        } else {
            return memberDAO.findOne(id);
        }
    }

    @Override
    public boolean create(Member member) {
        if (isValid(member) && !memberDAO.exists(member.getUsername())) {
            memberDAO.save(member);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Member getPublic(Member member) {
        return new Member(member.getUsername());
    }

    @Override
    public boolean update(Member member) {
        if (memberDAO.exists(member.getUsername())) {
            memberDAO.save(member);
//            contactInformationDAO.save(member.getContactInformation());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        if (memberDAO.exists(id)) {
            contactInformationDAO.delete(id);
            memberDAO.delete(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValid(Member member) {
        return !(member.getUsername().isEmpty());
    }

    @Override
    public Set<Member> getMembersFromOrganization(Organization organization) {
        return new HashSet<>(memberDAO.findAllByUsernameIn(organization.getMembers()));
    }

    @Override
    public Set<Member> getAdminsFromOrganization(Organization organization) {
        return new HashSet<>(memberDAO.findAllByUsernameIn(organization.getAdmin()));
    }

    @Override
    public void addOrganization(String username, String organizationId) {
        Member member = this.get(username);
        member.addOrganization(organizationId);
        this.update(member);
    }

    @Override
    public void removeOrganization(String username, String organizationId) {
        Member member = this.get(username);
        member.removeOrganization(organizationId);
        this.update(member);
    }

    @Override
    public boolean exists(String id) {
        return memberDAO.exists(id);
    }
}
