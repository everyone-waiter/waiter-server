package handwoong.waiter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import handwoong.waiter.domain.Member;
import handwoong.waiter.domain.Waiting;
import handwoong.waiter.form.WaitingForm;
import handwoong.waiter.repository.MemberRepository;
import handwoong.waiter.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sens.MessageService;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WaitingService {

	private final WaitingRepository waitingRepository;
	private final MemberRepository memberRepository;
	private final MessageService messageService;

	public Waiting findOne(UUID waitingId) {
		return waitingRepository.findOne(waitingId);
	}

	public List<Waiting> findWaitingList(UUID memberId) {
		return waitingRepository.findAll(memberId);
	}

	@Transactional
	public Waiting register(UUID memberId, WaitingForm waitingForm) {
		Member member = memberRepository.findOne(memberId);
		int waitingTurn = member.getWaitingList().size();
		int waitingNumber = waitingTurn + 1;

		Waiting waiting = Waiting.createWaiting(
			member, waitingNumber, waitingTurn,
			waitingForm.getAdult(), waitingForm.getChildren(), waitingForm.getPhoneNumber());

		waitingRepository.save(waiting);
		member.addWaiting(waiting);
		// TODO 알림톡
		return waiting;
	}

	@Transactional
	public void deleteWaiting(UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId);
		waiting.cancel();
	}

	public void cancelWaiting(UUID waitingId) {
		deleteWaiting(waitingId);
		// TODO 알림톡
	}

	public void notice(UUID memberId, UUID waitingId) {
		Waiting waiting = waitingRepository.findOne(waitingId);
		// TODO 알림톡
		readyNotice(memberId, waitingId);
	}

	private void readyNotice(UUID memberId, UUID waitingId) {
		List<Waiting> waitingList = waitingRepository.findAll(memberId);
		boolean isFirstWaiting = waitingList.get(0).getId().equals(waitingId);

		if (!isFirstWaiting || waitingList.size() < 3) {
			return;
		}

		Waiting thirdWaiting = waitingList.get(2);
		if (!thirdWaiting.isSendMessage()) {
			// TODO 알림톡
			thirdWaiting.changeStatus();
		}
	}
}
