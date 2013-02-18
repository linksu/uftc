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
						<p><b> UFTC =	Ultimate Functional Training Challenge </b></p>
						<p>	UFTC on verkkosovellus, joka on toteutettu Java-ohjelmointikielell&auml Spring Frameworkia hy&oumldynt&aumlen.</p>
						<p>	Haastajana sin&auml voit luoda haasteita yhteis&oumlsi j&aumlsenille ja jopa osallistua niihin tai muiden luomiin haasteisiin. </p>
						<p>	Perusk&aumlytt&aumlj&aumln&auml sin&auml voit osallistua mihin tahansa yhteis&oumlsi haasteeseen, johon sinut on kutsuttu.</p>
						<br />
						<p>	Esimerkkihaaste: </p>
						<p><i> "kuka kuntoilee, esim. punnertaa tai h&oumllkk&auml&auml, eniten ty&oumlp&aumliv&aumln aikana?"</i></p>
						<br />
						<p>	Tehdyt suoritukset kirjataan UFTC-sovellukseen.</p>
						<p> Voit my&oumls kannustaa muita haasteeseen osallistujia, seurata heid&aumln edistymist&auml&aumln ja kommentoida heid&aumln suorituksiaan.</p>
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
						<p><b> UFTC = Ultimate Functional Training Challenge </b></p>
						<p>	UFTC is web-based application coded in Java utilizing the Spring Framework. </p>
						<p>	As a challenger you can create exercising challenges for your community and also participate in them yourself. </p>
						<p> As a normal user you can partcipate in any challenge if you belong to some community and owner of the challenge has invited you to join his challenge.</p>
						<br />
						<p>	Example-challenge: </p>
						<p><i>"who does the most exercising during the work day e.g. push-ups or jogging?"</i></p>
						<br />
						<p>	Done exercises are logged into the UFTC-application. </p>
						<p> You can also cheer other participants of the challenge and follow or comment their achievements.
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