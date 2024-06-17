package team.sparta.onehouronemeal.domain.admin.service.v1

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import team.sparta.onehouronemeal.domain.comment.model.v1.Comment
import team.sparta.onehouronemeal.domain.comment.model.v1.CommentStatus
import team.sparta.onehouronemeal.domain.comment.model.v1.report.Report
import team.sparta.onehouronemeal.domain.comment.model.v1.report.ReportStatus
import team.sparta.onehouronemeal.domain.comment.repository.v1.report.ReportRepository
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.course.model.v1.CourseStatus
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserRole
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import java.util.Optional

class AdminServiceTest : BehaviorSpec({

    val userRepository: UserRepository = mockk()
    val courseRepository: CourseRepository = mockk()
    val reportRepository: ReportRepository = mockk()
    val adminService = AdminService(userRepository, courseRepository, reportRepository)

    afterContainer {
        clearAllMocks()
    }

    val mockUser = mockk<User>()
    val mockComment = mockk<Comment>()

    given("PENDING 상태인 User에") {
        `when`("confirmSignUp() 실행하면") {
            then("User의 상태가 ACTIVE로 변경된다.") {
                val pendingUser = User(
                    username = "test",
                    password = "test",
                    profile = Profile(nickname = "test"),
                    role = UserRole.CHEF,
                    status = UserStatus.PENDING
                )

                every { userRepository.findById(any()) } returns pendingUser
                adminService.confirmSignUp(1L)

                pendingUser.status shouldBe UserStatus.ACTIVE
                verify { userRepository.findById(1L) }
            }
        }
        `when`("rejectSignUp() 실행하면") {
            then("User의 상태가 DINIED로 변경된다.") {
                val pendingUser = User(
                    username = "test",
                    password = "test",
                    profile = Profile(nickname = "test"),
                    role = UserRole.CHEF,
                    status = UserStatus.PENDING
                )

                every { userRepository.findById(any()) } returns pendingUser
                adminService.rejectSignUp(1L)

                pendingUser.status shouldBe UserStatus.DENIED
                verify { userRepository.findById(1L) }
            }
        }
    }

    given("PENDING 상태가 아닌 User에") {
        `when`("confirmSignUp() 실행하면") {
            then("IllegalStateException 발생한다.") {
                val activeUser = User(
                    username = "test",
                    password = "test",
                    profile = Profile(nickname = "test"),
                    role = UserRole.CHEF,
                    status = UserStatus.ACTIVE
                )

                every { userRepository.findById(any()) } returns activeUser

                shouldThrow<IllegalStateException> {
                    adminService.confirmSignUp(1L)
                }
                verify { userRepository.findById(1L) }
            }
        }

        `when`("rejectSignUp() 실행하면") {
            then("IllegalStateException 발생한다.") {
                val activeUser = User(
                    username = "test",
                    password = "test",
                    profile = Profile(nickname = "test"),
                    role = UserRole.CHEF,
                    status = UserStatus.ACTIVE
                )

                every { userRepository.findById(any()) } returns activeUser

                shouldThrow<IllegalStateException> {
                    adminService.rejectSignUp(1L)
                }
                verify { userRepository.findById(1L) }
            }
        }
    }

    given("존재하지 않는 User에") {
        `when`("confirmSignUp() 실행하면") {
            then("ModelNotFoundException 발생한다.") {
                every { userRepository.findById(any()) } returns null

                shouldThrow<ModelNotFoundException> {
                    adminService.confirmSignUp(1L)
                }
                verify { userRepository.findById(1L) }
            }
        }

        `when`("rejectSignUp() 실행하면") {
            then("IllegalStateException 발생한다.") {
                every { userRepository.findById(any()) } returns null

                shouldThrow<ModelNotFoundException> {
                    adminService.rejectSignUp(1L)
                }
                verify { userRepository.findById(1L) }
            }
        }
    }

    given("PENDING 상태인 Course에") {
        `when`("confirmCourse() 실행하면") {
            then("Course의 status가 OPEN으로 변경된다.") {
                val pendingCourse = Course(
                    id = 1L,
                    title = "Pending course",
                    describe = "test",
                    status = CourseStatus.PENDING,
                    user = mockUser
                )

                every { courseRepository.findById(any()) } returns Optional.of(pendingCourse)
                adminService.confirmCourse(1L)

                pendingCourse.status shouldBe CourseStatus.OPEN
                verify { courseRepository.findById(1L) }
            }
        }
        `when`("rejectCourse() 실행하면") {
            then("Course의 status가 DINIED로 변경된다.") {
                val pendingCourse = Course(
                    id = 1L,
                    title = "Pending course",
                    describe = "test",
                    status = CourseStatus.PENDING,
                    user = mockUser
                )

                every { courseRepository.findById(any()) } returns Optional.of(pendingCourse)
                adminService.rejectCourse(1L)

                pendingCourse.status shouldBe CourseStatus.DENIED
                verify { courseRepository.findById(1L) }
            }
        }
    }

    given("PENDING 상태가 아닌 Course에") {
        `when`("confirmCourse() 실행하면") {
            then("IllegalStateException이 발생한다.") {
                val openCourse = Course(
                    id = 1L,
                    title = "Pending course",
                    describe = "test",
                    status = CourseStatus.OPEN,
                    user = mockUser
                )

                every { courseRepository.findById(any()) } returns Optional.of(openCourse)

                shouldThrow<IllegalStateException> {
                    adminService.confirmCourse(1L)
                }
                verify { courseRepository.findById(1L) }
            }
        }
        `when`("rejectCourse() 실행하면") {
            then("IllegalStateException이 발생한다.") {
                val openCourse = Course(
                    id = 1L,
                    title = "Pending course",
                    describe = "test",
                    status = CourseStatus.OPEN,
                    user = mockUser
                )

                every { courseRepository.findById(any()) } returns Optional.of(openCourse)

                shouldThrow<IllegalStateException> {
                    adminService.rejectCourse(1L)
                }
                verify { courseRepository.findById(1L) }
            }
        }
    }

    given("존재하지 않는 Course에") {
        `when`("confirmCourse() 실행하면") {
            then("ModelNotFoundException이 발생한다.") {
                every { courseRepository.findById(any()) } returns Optional.empty()

                shouldThrow<ModelNotFoundException> {
                    adminService.confirmCourse(1L)
                }
                verify { courseRepository.findById(1L) }
            }
        }
        `when`("rejectCourse() 실행하면") {
            then("ModelNotFoundException이 발생한다.") {
                every { courseRepository.findById(any()) } returns Optional.empty()

                shouldThrow<ModelNotFoundException> {
                    adminService.rejectCourse(1L)
                }
                verify { courseRepository.findById(1L) }
            }
        }
    }

    given("존재하는 Report에") {
        `when`("acceptReport() 실행하면") {
            then("Report의 status가 ACCEPTED로 변경된다.") {
                val report = Report(
                    id = 1L,
                    comment = mockComment,
                    user = mockUser,
                    describe = "bad comment",
                    status = ReportStatus.ACTIVE,
                )
                every { reportRepository.findById(any()) } returns Optional.of(report)
                every { mockComment.changeStatus(CommentStatus.BLOCKED) } returns Unit

                adminService.acceptReport(1L)

                report.status shouldBe ReportStatus.ACCEPTED
                verify { reportRepository.findById(1L) }
            }
        }

        `when`("rejectReport() 실행하면") {
            then("Report가 삭제된다(soft delete).") {
                val report = Report(
                    id = 1L,
                    comment = mockComment,
                    user = mockUser,
                    describe = "bad comment",
                    status = ReportStatus.ACTIVE,
                )
                every { reportRepository.findById(any()) } returns Optional.of(report)
                every { reportRepository.delete(any()) } returns Unit

                adminService.rejectReport(1L)

                verify { reportRepository.findById(1L) }
                verify { reportRepository.delete(report) }
            }
        }
    }

    given("존재하지 않는 Report에") {
        `when`("acceptReport() 실행하면") {
            then("ModelNotFoundException이 발생한다.") {
                every { reportRepository.findById(any()) } returns Optional.empty()

                shouldThrow<ModelNotFoundException> {
                    adminService.acceptReport(1L)
                }
                verify { reportRepository.findById(1L) }
            }
        }
        `when`("rejectReport() 실행하면") {
            then("ModelNotFoundException이 발생한다.") {
                every { reportRepository.findById(any()) } returns Optional.empty()

                shouldThrow<ModelNotFoundException> {
                    adminService.rejectReport(1L)
                }
                verify { reportRepository.findById(1L) }
            }
        }
    }
})