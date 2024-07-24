<script>
	export default {
		globalData: {
			URL: '/practice/api',
			token: 'wbgsip900wkuUEcPHUExBe1716947239',
			imgPath: '/static/uploads/images/',
			filePath: '/static/uploads/files/'
		},
		onLaunch: function() {
			const token1 = 'wbgsip900wkuUEcPHUExBe1716947239';
			// const token2 = 'hdS7fivWq99rSqgkuIVMpj1717031362';
			// const api = 'https://api.campushoy.com/user/campus/get_user_info?access_token=';
			// const url = 'https://82n826t960.vicp.fun';
			// const url2 = 'https://8t282696k0.zicp.fun';
			const url3 = '/practice/api';
			// 小程序向后端发送token，后端验证后，携带data传回
			const path = '/teacher/login';
			const token = token1;
			uni.request({
				method: 'POST',
				url: url3 + path,
				header: {
					'token': token
				},
				success: (res) => {
					console.log(res);
					if (res.data.code === 0) {
						console.log("未登录，跳转到登录界面");
						// uni.redirectTo({
						// 	url: "/pages/login/login?msg='未登录，跳转到登录界面'",
						// });
						return;
					}
					// 小程序记录后端传回的token
					uni.setStorageSync('token', token);
					// 老师端
					if (res.data.data === 1) {
						uni.reLaunch({
							url: "/pages/examine/examine"
						})
					};
					// 学生端
					if (res.data.data === 2) {
						// TODO: 上线时恢复注释
						// uni.reLaunch({
						// 	url: "/pages/index/index"
						// })
					};
				},
				fail: (error) => {
					// console.log(error);
					// console.log("登录超时，请返回i上商跳转！");
					// uni.redirectTo({
					// 	url: "/pages/login/login?msg='登录超时，请返回i上商跳转！'"
					// });
				}
			})
		}
	}
</script>

<style lang="scss">
	@import "@/uni_modules/uview-ui/index.scss";
</style>