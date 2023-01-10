package hello.hellospring.Service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional // jpa 사용하려면 항상 필요함
public class MemberService {

    /**
    *  회원 가입
    */
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        // 같은 이름이 있는 중복 회원 X

        /**
         * null 을 리턴할 가능성이 있으면 Optional을 사용하는것을 권장
         */

        //memberRepository.findByName(member.getName()); -> ctrl + art + v 하면 아래줄로 변환
//        Optional<Member> result = memberRepository.findByName(member.getName());
//
//        result.ifPresent(m->{
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // 위의 코드와 같음
        validateDuplicateMember(member);


        memberRepository.save(member);
        return  member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m->{throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
       return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
