<script>
	export default {
		globalData: {
			// URL: '/practice/api',
			URL: 'http://127.0.0.1:8080',
			// token1: ' ',
			// token2: '6Y8wNHSHjiCruw1WXRT7js1731404576',
			// token3: 'R8qnHrP3cZBzY42XrdkutJ1731457639',
			imgPath: '/static/uploads/images/',
			filePath: '/static/uploads/files/',
			teacherFilePath: '/static/uploads/teacher/files/',
			// Oauth2 回调地址
			redirectUrl: 'https://yzs.sbs.edu.cn/practice/pages/login/login',
			// Oauth2 api调用地址
			api: 'https://api.campushoy.com',
			// Oauth2 clientId 应用id
			clientId: 17313155825670722,
			// 页面跳转路径
			pagePath: {
				// 全局
				login: '/pages/login/login',
				webView: '/pages/webView/webView',

				// 学生
				index: '/pages/student/index/index',
				setup: '/pages/student/setup/setup',
				midTerm: '/pages/student/midTerm/midTerm',
				endTerm: '/pages/student/endTerm/endTerm',
				date: '/pages/student/date/date',
				news: '/pages/student/news/news',
				newsEditor: '/pages/student/newsEditor/newsEditor',
				preview: '/pages/student/preview/preview',
				newsFinish: '/pages/student/newsFinish/newsFinish',
				newsDetail: '/pages/student/newsDetail/newsDetail',
				finish: '/pages/student/finish/finish',
				crop: '/pages/student/crop/crop',
				show: '/pages/student/show/show',
				mine: '/pages/student/mine/mine',
				noticeDetail: '/pages/student/noticeDetail/noticeDetail',

				// 老师
				tIndex: '/pages/teacher/tIndex/tIndex',
				tSetup: '/pages/teacher/tSetup/tSetup',
				tMidTerm: '/pages/teacher/tMidTerm/tMidTerm',
				tEndTerm: '/pages/teacher/tEndTerm/tEndTerm',
				tDate: '/pages/teacher/tDate/tDate',
				tNews: '/pages/teacher/tNews/tNews',
				tNewsDetail: '/pages/teacher/tNewsDetail/tNewsDetail',
				tSubject: '/pages/teacher/tSubject/tSubject',
				tManage: '/pages/teacher/tManage/tManage',
				tMine: '/pages/teacher/tMine/tMine',
				tShow: '/pages/teacher/tShow/tShow',
				tNotice: '/pages/teacher/tNotice/tNotice',
				tNoticeEditor: '/pages/teacher/tNoticeEditor/tNoticeEditor',
			}
		},
		onLaunch: function() {
			// 测试token
			// const token = 'bccg0-n-WYF8m-2qGYMUmv1730254660';

			const url3 = 'http://127.0.0.1:8080';
			// const url3 = '/practice/api';
			const teacherPage = '/pages/teacher/tIndex/tIndex';
			const studentPage = '/pages/student/index/index';
			// TODO: 上线删除
			uni.setStorageSync('token', '6Y8wNHSHjiCruw1WXRT7js1731404576');
		}
	}
</script>

<style lang="scss">
	@import "@/uni_modules/uview-ui/index.scss";
</style>