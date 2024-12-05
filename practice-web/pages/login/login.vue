<template>
	<view class="container">
		<u-notify ref="uNotify"></u-notify>
		<view class="text" v-if="!src">
			正在登录，请稍后...
		</view>
		<view class="web-view" v-if="src">
			<web-view :webview-styles="webviewStyles" :src="src"></web-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 第一步返回的code
				code: 0,
				// webView的url
				src: '',
				webviewStyles: {
					progress: {
						color: '#FF3333'
					}
				},
			}
		},
		methods: {
			// 跳转到Oauth2获取code
			getCode() {
				console.log("getCode");
				const PATH = '/connect/oauth2/authorize';
				const SCOPE = 'basic_info';
				const STATE = 1;
				// https://api.campushoy.com/connect/oauth2/authorize?
				// response_type=code
				// &client_id=17313155825670722
				// &scope=basic_info
				// &state=1
				// &redirect_uri=https%3A%2F%2Fyzs.sbs.edu.cn%2Fpractice%2F
				this.src =
					`${getApp().globalData.api}${PATH}?response_type=code&state=${STATE}&scope=${SCOPE}&client_id=${getApp().globalData.clientId}&redirect_uri=${encodeURIComponent(getApp().globalData.redirectUrl)}`;
				console.log(this.src);
				// uni.reLaunch({
				// 	url: `${getApp().globalData.pagePath.webView}?url=${getApp().globalData.api}${PATH}?response_type=code&state=${STATE}&scope=${SCOPE}&client_id=${getApp().globalData.clientId}&redirect_uri=${encodeURIComponent(getApp().globalData.redirectUrl)}`
				// })
			},
			// 将code返回到后端，由后端取token
			getToken(code) {
				const PATH = '/oauth/getToken';
				console.log("getToken");
				uni.request({
					url: `${getApp().globalData.URL}${PATH}?code=${code}&redirect_uri=${getApp().globalData.redirectUrl}`,
					method: 'GET',
					header: {},
					// token 获取成功
					success: (res) => {
						// 保存token

						const token = res.data.data;
						console.log(`token:${token}`)
						uni.setStorageSync('token', token);
						uni.redirectTo({
							url: getApp().globalData.pagePath.login
						})
					},
					fail: (error) => {
						console.log("token获取失败");
						this.notify('error', '网络异常！');
					}
				})
			},

			// 验证身份
			async verify() {
				const PATH = '/teacher/login';
				console.log(`token:${uni.getStorageSync('token')}`);
				const res = await uni.request({
					method: 'POST',
					url: getApp().globalData.URL + PATH,
					header: {
						'token': uni.getStorageSync('token'),
					},
				});
				// 通过code，选择跳转
				console.log(res);
				const code = res.data.code;
				console.log(code);
				// 小程序记录后端传回的token

				// 老师端
				if (res.data.data === 1) {
					console.log("教师端");
					await uni.reLaunch({
						url: getApp().globalData.pagePath.tIndex,
					})
				};
				// 学生端
				if (res.data.data === 2) {
					// TODO: 上线时恢复注释
					console.log("学生端");
					await uni.reLaunch({
						url: getApp().globalData.pagePath.index,
					})
				};
				// 失败后返回
				console.log("登录失败");
				this.notify('error', res.data.msg);
				return;
			},
			// 错误提示
			notify(type, message) {
				this.$refs.uNotify.show({
					top: 10,
					type,
					message,
					duration: 1000 * 3,
					fontSize: 20,
				})
			}

		},
		async onLoad(options) {
			// 验证成功直接跳转，验证失败向下执行
			await this.verify();
			console.log(options.code);
			// 如果没有code，调用getCode();
			// 如果有code, 调用getToken();
			options.code ? this.getToken(options.code) : this.getCode();
			console.log(options.code);
			console.log(this.code);
		},
	}
</script>

<style>

</style>