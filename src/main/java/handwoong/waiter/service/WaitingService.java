package handwoong.waiter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import handwoong.waiter.domain.Member;
import handwoong.waiter.domain.Waiting;
import handwoong.waiter.domain.WaitingStatus;
import handwoong.waiter.dto.WaitingRequestDto;
import handwoong.waiter.exception.NotFoundMemberException;
import handwoong.waiter.exception.NotFoundWaitingException;
import handwoong.waiter.repository.MemberRepository;
import handwoong.waiter.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sens.MessageService;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WaitingService {

	private final WaitingRepository waitingRepository;
	private final MemberRepository memberRepository;
	private final MessageService messageService;

	@Transactional(readOnly = true)
	public Waiting findOne(UUID waitingId) {
		return waitingRepository.findOne(waitingId).orElseThrow(NotFoundWaitingException::new);
	}

	@Transactional(readOnly = true)
	public List<Waiting> findWaitingList(UUID memberId) {
		return waitingRepository.findAll(memberId);
	}

	public Waiting register(UUID memberId, WaitingRequestDto waitingRequestDto) {
		Member member = memberRepository.findOne(memberId).orElseThrow(NotFoundMemberException::new);
		Waiting waiting = Waiting.createWaiting(
			member, waitingRequestDto.getAdult(), waitingRequestDto.getChildren(), waitingRequestDto.getPhoneNumber());
		waitingRepository.save(waiting);
		// TODO 알림톡
		return waiting;
	}

	public void deleteWaiting(UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId).orElseThrow(NotFoundWaitingException::new);
		waiting.cancel(WaitingStatus.ENTER);
	}

	public void cancelWaiting(UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId).orElseThrow(NotFoundWaitingException::new);
		waiting.cancel(WaitingStatus.CANCEL);
		// TODO 알림톡
	}

	public void notice(UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId).orElseThrow(NotFoundWaitingException::new);
		// TODO 알림톡

		waiting.readyNotice().ifPresent(thirdWaiting -> {
			// TODO 알림톡
			thirdWaiting.changeSendStatus();
		});
	}
}
