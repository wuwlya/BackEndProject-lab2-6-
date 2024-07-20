package back.example.project.controller;

import back.example.project.dto.MemberCreateDto;
import back.example.project.dto.MemberDto;
import back.example.project.dto.MemberResponseDto;
import back.example.project.service.MemberService;
import back.example.project.exception.ControllerExceptionHandler;
import back.example.project.exception.NotFoundException;
import back.example.project.models.Member;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/member")
@RestController
@ControllerExceptionHandler
public class MemberController {
    private final MemberService memberService;
    private final ModelMapper modelMapper;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MemberDto> GetMember() {
        List<Member> members = memberService.getMembers();
        List<MemberDto> memberListDto = new ArrayList<>();

        for (Member member : members) {
            MemberDto memberDto = modelMapper.map(member, MemberDto.class);
            memberListDto.add(memberDto);
        }
        if (memberListDto.isEmpty()) {
            throw new NotFoundException("Members not found in database");
        }

        return memberListDto;
    }

    @GetMapping(path = "{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberDto getMember(@PathVariable long memberId) {
        Member member = memberService.findMemberById(memberId);
        MemberDto memberDto = modelMapper.map(member, MemberDto.class);

        return memberDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MemberResponseDto createMember(@RequestBody MemberCreateDto memberCreateDto) {
        Member newMember = modelMapper.map(memberCreateDto, Member.class);

        var memberId = memberService.addNewMember(newMember);
        var memberCreationResponseDto = new MemberResponseDto().setId(memberId);

        return memberCreationResponseDto;
    }

    @DeleteMapping(path = "{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponseDto deleteMember(@PathVariable("memberId") Long id){
        MemberResponseDto memberResponseDto = new MemberResponseDto().setId(memberService.deleteMember(id));

        return memberResponseDto;
    }

    @PutMapping(path = "{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberDto updateMember (@PathVariable("memberId") Long memberId) {
        memberService.switchCompleted(memberId);
        return modelMapper.map(memberService.findMemberById(memberId), MemberDto.class);
    }

}