	<%@ include file="/templates/header.jsp"%>
		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext"><spring:message code="uftc.openChallenges" text="Open challenges"/></h2>
				</div>

				<div class="top5">
					<h3 class="text1">ULTIMATE TOP 5</h3>
					<h3 class="text2"><spring:message code="uftc.points" text="Points"/></h3>
				</div>

				<div class="top5_content">

					<c:forEach items="${challengeList}" var="challenge">
						<div class="top5_content_box">
							<div class="group">
								<a href="/uftc/challenge/show?challengeId=${challenge.getId()}"><c:out
										value="${challenge.title}" /></a>
							</div>
							<div class="points">
								<h5>${challenge.getTotalPoints()}</h5>
							</div>
						</div>
					</c:forEach>

					<div class="alanappi1">
						<a href="#"><img src="pics/katsolisaa.png"></a>
					</div>

				</div>
				<!--End of top5_content-->
			</div>
			<!--End of column1-->

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">OSALLISTU TAI HAASTA</h2>
				</div>
				<div class="leipis">
					<div class="chartbox">
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Mauris vel libero sed risus consectetur vestibulum id cursus elit.
							Morbi venenatis hendrerit orci, sed imperdiet lectus pulvinar
							eget. Ut nulla ligula, rhoncus a mollis ultrices, hendrerit sed
							odio. Donec consectetur volutpat lacinia. Morbi et magna vel
							sapien porta rutrum. Aenean auctor porta arcu pellentesque
							tincidunt. Aenean nibh quam, condimentum vitae eleifend non,
							commodo id ipsum. Curabitur consequat tincidunt arcu in egestas.
							Maecenas in leo at urna sagittis elementum eu eget turpis. Quisque
							magna sem, lobortis sit amet sollicitudin sed, vehicula non
							tortor. Phasellus facilisis nunc eget arcu tempus semper. Duis
							blandit neque et dui fermentum id egestas odio dictum. Quisque eu
							ante tortor, a laoreet augue. Maecenas aliquet condimentum eros,
							et laoreet leo hendrerit nec. Integer ultricies pretium libero in
							hendrerit. Quisque imperdiet dapibus nunc, vitae cursus felis
							eleifend eu.</p>
					</div>
				</div>
				<div class="alanappi2">
					<a href="#"><img src="pics/alanappi2.png"></a>
				</div>
			</div>
			<!--End if column2-->

			<div id="column3">
				<div class="headerbox">
					<h2 class="headertext">PARTICIPATE OR CHALLENGE</h2>
				</div>
				<div class="leipis">
					<div class="chartbox">
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Mauris vel libero sed risus consectetur vestibulum id cursus elit.
							Morbi venenatis hendrerit orci, sed imperdiet lectus pulvinar
							eget. Ut nulla ligula, rhoncus a mollis ultrices, hendrerit sed
							odio. Donec consectetur volutpat lacinia. Morbi et magna vel
							sapien porta rutrum. Aenean auctor porta arcu pellentesque
							tincidunt. Aenean nibh quam, condimentum vitae eleifend non,
							commodo id ipsum. Curabitur consequat tincidunt arcu in egestas.
							Maecenas in leo at urna sagittis elementum eu eget turpis. Quisque
							magna sem, lobortis sit amet sollicitudin sed, vehicula non
							tortor. Phasellus facilisis nunc eget arcu tempus semper. Duis
							blandit neque et dui fermentum id egestas odio dictum. Quisque eu
							ante tortor, a laoreet augue. Maecenas aliquet condimentum eros,
							et laoreet leo hendrerit nec. Integer ultricies pretium libero in
							hendrerit. Quisque imperdiet dapibus nunc, vitae cursus felis
							eleifend eu.</p>
					</div>
				</div>
				<div class="alanappi1">
					<a href="#"><img src="pics/katsolisaa.png"></a>
				</div>
			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>