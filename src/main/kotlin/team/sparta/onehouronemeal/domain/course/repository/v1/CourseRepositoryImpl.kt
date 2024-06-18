package team.sparta.onehouronemeal.domain.course.repository.v1

import com.querydsl.jpa.JPAExpressions
import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.comment.model.v1.QComment
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.course.model.v1.CourseStatus
import team.sparta.onehouronemeal.domain.course.model.v1.QCourse
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.QThumbsUp
import team.sparta.onehouronemeal.domain.recipe.model.v1.QRecipe
import team.sparta.onehouronemeal.domain.user.model.v1.QUser
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.QSubscription
import team.sparta.onehouronemeal.infra.querydsl.QueryDslSupport
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Repository
class CourseRepositoryImpl : CustomCourseRepository, QueryDslSupport() {

    private val course = QCourse.course
    private val recipe = QRecipe.recipe
    private val comment = QComment.comment
    private val thumbsUp = QThumbsUp.thumbsUp
    private val subscription = QSubscription.subscription
    private val user = QUser.user

    override fun searchOpenCourseListWithThumbsUpCount(): Pair<List<Course>, List<Long>> {
        val openCourseListWithUser = queryFactory
            .selectFrom(course)
            .leftJoin(course.user, user).fetchJoin()
            .where(course.status.eq(CourseStatus.OPEN))
            .orderBy(course.createdAt.desc())
            .fetch()

        val count = queryFactory
            .select(
                course.id,
                thumbsUp.course.id.count()
            )
            .from(course)
            .leftJoin(thumbsUp).on(thumbsUp.course.id.eq(course.id))
            .where(course.status.eq(CourseStatus.OPEN))
            .groupBy(course.id)
            .orderBy(course.createdAt.desc())
            .fetch()

        val countList = count.map { it.get(thumbsUp.course.id.count())!! }

        return openCourseListWithUser to countList
    }

    override fun findByIdOrNullWithUser(courseId: Long): Course? {
        return queryFactory
            .selectFrom(course)
            .leftJoin(course.user, user).fetchJoin()
            .where(course.id.eq(courseId))
            .fetchOne()
    }

    override fun getOpenCourseListBySubscribedChefs(principal: UserPrincipal): Pair<List<Course>, List<Long>> {
        val openCourseListBySubscribedChefs = queryFactory
            .selectFrom(course)
            .leftJoin(course.user, user).fetchJoin()
            .where(
                course.status.eq(CourseStatus.OPEN).and(
                    course.user.id.`in`(
                        JPAExpressions.select(subscription.subscribedUser.id).from(subscription)
                            .where(subscription.user.id.eq(principal.id))
                    )
                )
            )
            .orderBy(course.createdAt.desc())
            .fetch()

        val count = queryFactory
            .select(
                course.id,
                thumbsUp.course.id.count()
            )
            .from(course)
            .leftJoin(thumbsUp).on(thumbsUp.course.id.eq(course.id))
            .where(
                course.status.eq(CourseStatus.OPEN).and(
                    course.user.id.`in`(
                        JPAExpressions.select(subscription.subscribedUser.id).from(subscription)
                            .where(subscription.user.id.eq(principal.id))
                    )
                )
            )
            .groupBy(course.id)
            .orderBy(course.createdAt.desc())
            .fetch()

        val countList = count.map { it.get(thumbsUp.course.id.count())!! }

        return openCourseListBySubscribedChefs to countList
    }

    override fun findAllPendingCourse(): List<Course> {
        return queryFactory
            .selectFrom(course)
            .leftJoin(course.user, user).fetchJoin()
            .where(course.status.eq(CourseStatus.PENDING))
            .orderBy(course.createdAt.desc())
            .fetch()
    }

    override fun deleteCourseWithRecipesCommentsThumbsUps(courseId: Long) {
        queryFactory.delete(recipe).where(recipe.course.id.eq(courseId)).execute()

        queryFactory.delete(comment).where(comment.course.id.eq(courseId)).execute()

        queryFactory.delete(thumbsUp).where(thumbsUp.course.id.eq(courseId)).execute()

        queryFactory.delete(course).where(course.id.eq(courseId)).execute()
    }
}