package handwoong.waiter.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import handwoong.waiter.domain.Waiting;
import handwoong.waiter.form.WaitingForm;
import handwoong.waiter.repository.WaitingRepository;
import handwoong.waiter.service.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WaitingController {

	private final WaitingService waitingService;
	private final WaitingRepository waitingRepository;

	// ================ VIEW ================

	@GetMapping("/waiting/{memberId}")
	public String waitingForm(@PathVariable String memberId, Model model) {
		Long count = waitingRepository.count(UUID.fromString(memberId));
		model.addAttribute("count", count);
		model.addAttribute("waitingForm", new WaitingForm());
		return "waiting/addForm";
	}

	@GetMapping("/waiting/result")
	public String resultForm(@ModelAttribute Waiting waiting) {
		return "waiting/result";
	}

	@GetMapping("/waiting/turn/{waitingId}")
	public String turnForm(@PathVariable String waitingId, Model model) {
		Waiting waiting = waitingService.findOne(UUID.fromString(waitingId));
		model.addAttribute("waiting", waiting);
		return "waiting/turn";
	}

	@GetMapping("/waiting/cancel/{waitingId}")
	public String cancelForm(@PathVariable String waitingId, Model model) {
		Waiting waiting = waitingService.findOne(UUID.fromString(waitingId));
		model.addAttribute("waiting", waiting);
		return "waiting/cancel";
	}

	@GetMapping("/waiting/admin/{memberId}")
	public String adminForm(@PathVariable String memberId, Model model) {
		List<Waiting> waitingList = waitingService.findWaitingList(UUID.fromString(memberId));
		model.addAttribute("waitingList", waitingList);
		return "waiting/admin";
	}

	// ======================================

	@GetMapping("/waiting/{memberId}/reload")
	public String replaceWaitingForm(@PathVariable String memberId, Model model) {
		Long count = waitingRepository.count(UUID.fromString(memberId));
		model.addAttribute("count", count);
		return "waiting/addForm :: #target-reload";
	}

	@GetMapping("/waiting/admin/{memberId}/reload")
	public String replaceAdminForm(@PathVariable String memberId, Model model) {
		List<Waiting> waitingList = waitingService.findWaitingList(UUID.fromString(memberId));
		model.addAttribute("waitingList", waitingList);
		return "waiting/admin :: #target-reload";
	}

	@PostMapping("/waiting/{memberId}")
	public String waitingRegister(
		@PathVariable String memberId, @Validated WaitingForm waitingForm,
		Errors errors, Model model, RedirectAttributes redirectAttributes) {
		UUID memberUUID = UUID.fromString(memberId);

		if (errors.hasErrors()) {
			Long count = waitingRepository.count(UUID.fromString(memberId));
			model.addAttribute("count", count);
			return "waiting/addForm";
		}

		Waiting waiting = waitingService.register(memberUUID, waitingForm);
		redirectAttributes.addFlashAttribute("waiting", waiting);
		redirectAttributes.addFlashAttribute("memberId", memberId);
		return "redirect:/waiting/result";
	}

	@PostMapping("/waiting/admin/notice/{waitingId}")
	@ResponseBody
	public String waitingNotice(@PathVariable String waitingId) {
		waitingService.notice(UUID.fromString(waitingId));
		return "ok";
	}

	@DeleteMapping("/waiting/admin/{memberId}/delete/{waitingId}")
	public String adminDeleteWaiting(@PathVariable String memberId, @PathVariable String waitingId, Model model) {
		UUID memberUUID = UUID.fromString(memberId);
		UUID waitingUUID = UUID.fromString(waitingId);

		waitingService.deleteWaiting(waitingUUID);
		List<Waiting> waitingList = waitingService.findWaitingList(memberUUID);
		model.addAttribute("waitingList", waitingList);
		return "waiting/admin :: #target-reload";
	}

	@DeleteMapping("/waiting/cancel/{waitingId}")
	@ResponseBody
	public String userCancelWaiting(@PathVariable String waitingId) {
		waitingService.cancelWaiting(UUID.fromString(waitingId));
		return "ok";
	}
}
