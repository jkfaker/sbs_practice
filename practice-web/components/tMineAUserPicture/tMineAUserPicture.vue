<template>
	<view class="container">
		<view class="content">
			<u--image width="70" height="70" :src="userPic" shape="circle"></u--image>
			<view class="leader">
				<view class="name">

					{{ teacher.teacherName }}
				</view>
				<u-gap height="10"></u-gap>
				<view class="id">
					{{ teacher.teacherId }}
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		name: "mineAUserPicture",
		props: ['teacher'],
		data() {
			return {
				userPic: "/static/uploads/leader_header.png",
			};
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		justify-content: start;
	}

	.content {
		display: flex;
		flex-direction: row;
		justify-content: start;
		align-items: center;
	}

	.leader {
		margin-left: 20px;
	}
</style>