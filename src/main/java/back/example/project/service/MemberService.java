package back.example.project.service;

import back.example.project.exception.CreateException;
import back.example.project.exception.NotFoundException;
import back.example.project.models.Member;
import back.example.project.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long addNewMember(Member newMember) {
        if(newMember.getName().strip().isEmpty()){
            throw new CreateException("Name isn't empty");
        }
        if(newMember.getProfession().strip().isEmpty()){
            throw new CreateException("Please provide a profession, if you want to add a new member");
        }
        return memberRepository.save(newMember).getId();
    }

    public Long deleteMember(Long id) {
        memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id not found to delete"));
        memberRepository.deleteById(id);
        return id;
    }
    
    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(members::add);
        return members;
    }

    public Member findMemberById(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("id not found, please provide a valid id"));
    }

    @Transactional
    public void switchCompleted(Long memberId) {
        Member member = findMemberById(memberId);
        member.setActivity(!member.getActivity());
        memberRepository.save(member);
    }
}
