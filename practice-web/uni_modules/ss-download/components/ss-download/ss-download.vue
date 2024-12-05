<template>
	<view></view>
</template>

<script>
	export default {
		name: 'ss-download',
		props: {
			fileUrl: {
				type: String,
				default: ''
			},
			fileType: {
				type: String,
				default: '', //1.预览图片，2.预览文件，3.预览视频
			},
		},
		data() {
			return {};
		},
		methods: {
			toDownload(item) {
				// #ifdef H5
				this.downloadH5(item.src)
				// #endif
				// #ifdef MP-WEIXIN
				if(item.type == 1){
					this.saveToPhotosAlbum(item.src)
				} else if (item.type == 2) {
					this.saveFile(item.src)	
				}else{
					this.getDownVideo(item.src)
				}
				// #endif
				// #ifdef APP-PLUS
				this.saveFileToApp(item.src)
				// #endif
			},
			//H5下载图片到本地
			downloadH5(url) {
				uni.downloadFile({
					url: url, //仅为示例，并非真实的资源
					success: (res) => {
						console.log(res)
						if (res.statusCode === 200) {
							console.log('下载成功');
							var oA = document.createElement("a");
							oA.download = ''; // 设置下载的文件名，默认是'下载'
							oA.href = res.tempFilePath; //临时路径再保存到本地
							document.body.appendChild(oA);
							oA.click();
							oA.remove(); // 下载之后把创建的元素删除
						}
					}
				});
			},
			//微信小程序保存文档,不支持h5
			saveFile(url) {
				uni.showLoading()
				uni.downloadFile({ //下载文件资源到本地,返回文件的本地临时路径
					url: url, //网络图片路径
					success: (res) => {
						var filePath = res.tempFilePath;
						//保存到本地
						uni.saveFile({
							tempFilePath: filePath,
							success: function(res) {
								//res.savedFilePath文件的保存路径
								uni.openDocument({
									filePath: res.savedFilePath,
									fileType: 'pdf',
									showMenu: true,
									success: function(res) {
										uni.hideLoading()
									},
									fail: function(err) {
										uni.hideLoading()
									}
								});
							},
							fail(err) {
								console.log(err)
								uni.hideLoading()
								console.log('保存失败')
							}
						});
					}
				})
			},
			//微信小程序保存视频
			getDownVideo(url) {
				// 自定义 文件名称
				uni.showLoading({
					mask: true,
					title: '下载中...'
				})
				let fileName = new Date().valueOf();
				const task = uni.downloadFile({
					url: url,
					filePath: wx.env.USER_DATA_PATH + '/' + fileName + '.mp4', //  拼接本地文件路径
					success: (res) => {
						let filePath = res.filePath
						uni.saveVideoToPhotosAlbum({
							filePath,
							success: (res) => {
								uni.showToast({
									title: '下载成功',
									icon: 'success',
								})
								let fileMgr = wx.getFileSystemManager();
								// 删除本地文件
								fileMgr.unlink({
									filePath: wx.env.USER_DATA_PATH + '/' + fileName +
										'.mp4',
									success: function(r) {
										console.log('unlink-getFileSystemManager')
										console.log(r)
									},
								})
							},
							fail(err) {
								uni.showToast({
									title: '保存失败',
									icon: 'none',
								})
							},
							complete(res) {
								console.log('saveVideoToPhotosAlbum-complete')
								console.log(res)
								uni.hideLoading()
							}
						})
					},
					fail(err) {
						uni.showToast({
							title: '下载失败,请稍后再试',
							icon: 'none',
						})
					},
					complete(res) {
						console.log('downloadFiledownloadFiledownloadFiledownloadFiledownloadFile')
						console.log(res)
					}
				})
				task.onProgressUpdate(this.onProgress)
			},
			// 提示下载进度
			onProgress(res) {
				uni.showLoading({
					mask: true,
					title: res.progress ? '下载中' + res.progress + "%" : '下载中...'
				})
			},
			//微信小程序保存图片（此处也可用于app保存图片或视频）
			saveToPhotosAlbum(url) {
				// #ifdef APP-PLUS
				var showLoading = plus.nativeUI.showWaiting("正在下载"); //创建一个showWaiting对象 
				// #endif
				let task = uni.downloadFile({
					url: url,
					success: res => {
						const {
							statusCode,
							tempFilePath
						} = res
						if (statusCode === 200) { // saveImageToPhotosAlbum  saveVideoToPhotosAlbum  
							uni.saveImageToPhotosAlbum({ //此处也可用saveVideoToPhotosAlbum
								filePath: tempFilePath,
								success: data => {
									console.log('data----------------->', data)
									uni.showToast({
										title: '下载成功,文件已保存到' + data.path,
										icon: 'success',
									})
								},
								complete: (msg) => {
									// #ifdef APP-PLUS
									plus.nativeUI.closeWaiting()
									// #endif
								}
							});
						} else {
							uni.showToast({
								title: '下载失败',
								icon: 'none',
							})
							// #ifdef APP-PLUS
							plus.nativeUI.closeWaiting()
							// #endif
						}
					},
					complete: () => {

					}
				})
				task.onProgressUpdate((res => {
					// console.log('上传进度' + res.progress);
					// console.log('已经上传的数据长度' + res.totalBytesSent);
					// console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
					showLoading.setTitle("  正在下载" + res.progress + "%  ");
				}))
			},
			//移动端下载文件
			saveFileToApp(url){
				var dtask = plus.downloader.createDownload(url, {},
					function(d, status) {
						uni.showToast({
							title: '下载完成',
							mask: false,
							duration: 1000
						});
						//console.log(dtask);
						// 下载完成
						console.log('status: ' + status);
						if (status == 200) {
							console.log('下载成功：' + d.filename);
							console.log('plus.io.convertLocalFileSystemURL(d.filename): ' + plus.io
								.convertLocalFileSystemURL(d
									.filename))
							plus.runtime.openFile(plus.io.convertLocalFileSystemURL(d.filename), {}, function(success) {
								console.log('打开成功')
							}, function(error) {
								console.log('打开失败')
							})
						} else {
							uni.showToast({
								title: '下载失败-02',
								mask: false,
								duration: 1500
							});
						}
					});
				try {
					dtask.start(); // 开启下载的任务
					var prg = 0;
					var showLoading = plus.nativeUI.showWaiting("正在下载"); //创建一个showWaiting对象 
					dtask.addEventListener('statechanged', function(task, status) {
						// 给下载任务设置一个监听 并根据状态  做操作
						switch (task.state) {
							case 1:
								showLoading.setTitle("正在下载");
								break;
							case 2:
								showLoading.setTitle("已连接到服务器");
								break;
							case 3:
								prg = parseInt((parseFloat(task.downloadedSize) / parseFloat(task.totalSize)) * 100);
								showLoading.setTitle("  正在下载" + prg + "%  ");
								break;
							case 4:
								plus.nativeUI.closeWaiting();
								//下载完成
								break;
						}
					});
				} catch (err) {
					plus.nativeUI.closeWaiting();
					uni.showToast({
						title: '更新失败-03',
						mask: false,
						duration: 1500
					});
				}
			},

		},
		watch: {
			fileType: {
				deep: true,
				handler: function(value) {
					if(this.fileUrl){
						this.toDownload({
							type:this.fileType,
							src:this.fileUrl,
						})
					}	
				}
			}
		}
	};
</script>

