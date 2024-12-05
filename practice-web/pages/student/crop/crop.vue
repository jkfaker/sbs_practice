<template>
	<view class="container">
		<qfCropper @crop="crop($event)" :src="src" width="1280" height="720"></qfCropper>
	</view>
</template>

<script>
	import qfCropper from '@/uni_modules/qf-image-cropper/components/qf-image-cropper/qf-image-cropper.vue';
	export default {
		components: {
			qfCropper
		},
		data() {
			return {
				// 待裁剪的图片
				src: '',
				// 裁剪的图片
				imgCroped: '',
			}
		},
		async onLoad() {
			let imgData = await uni.chooseImage({
				count: 1,
			})
			console.log(imgData);
			const tempFilePath = imgData.tempFilePaths[0];
			console.log(tempFilePath);
			this.src = tempFilePath;
		},
		methods: {
			// 裁剪完后上传后端，获取图片名
			async crop(e) {
				const path = '/files/images';
				// 临时文件，base64
				console.log(e.tempFilePath);
				// 创建文件系统管理器

				// #ifdef MP-WEIXIN || APP || MP-QQ 
				const fs = uni.getFileSystemManager();
				// 定义文件保存的临时路径
				const tempPath = uni.env.USER_DATA_PATH + '/tempImage.jpg';
				const fsRes = await fs.writeFile({
					filePath: tempFilePath, // 文件保存路径
					data: e.tempFilePath.replace(/^data:image\/\w+;base64,/, ""), // 移除前缀
					encoding: 'base64',
					success: function(res) {
						console.log('图片写入成功');
					}
				})
				console.log(fsRes);
				// #endif

				// #ifdef H5
				// 移除数据URL的头部，只保留Base64编码部分
				let base64Image = e.tempFilePath.replace(/^data:image\/\w+;base64,/, "");

				// 创建Blob对象
				let byteString = atob(base64Image);
				let ab = new ArrayBuffer(byteString.length);
				let ia = new Uint8Array(ab);
				for (let i = 0; i < byteString.length; i++) {
					ia[i] = byteString.charCodeAt(i);
				}
				let blob = new Blob([ab], {
					type: "image/jpeg"
				});
				let fileName = "example.jpeg"; // 或者使用动态生成的文件名

				// 创建一个URL表示这个Blob
				let tempPath = URL.createObjectURL(blob);

				// 使用tempPath，它可以被视为图片的临时路径
				console.log('图片临时路径:', tempPath);

				// 当不再需要这个URL时，记得释放资源
				// URL.revokeObjectURL(tempPath);
				// #endif

				// 上传至服务器，返回imgName
				let uploadData = await uni.uploadFile({
					url: getApp().globalData.URL + path,
					filePath: tempPath,
					name: 'img',
					header: {
						'token': uni.getStorageSync('token'),
					},
				})
				URL.revokeObjectURL(tempPath);
				console.log(uploadData);
				const imgName = JSON.parse(uploadData.data).data;
				console.log(`imgName:${imgName}`);
				uni.setStorageSync('cropImageName', imgName);
				// 带着imgName返回原页面
				uni.navigateBack({
					delta: 1,
				});
			},
		}
	}
</script>

<style lang="less" scoped>
	.container {
		height: calc(100vh - var(--window-top));
	}
</style>