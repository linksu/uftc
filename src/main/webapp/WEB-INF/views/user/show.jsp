<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext">OMA HAASTE</h2>
				</div>
				<div class="chartbox">
					<div class="challenge">
						<div class="upper-header">
							<h3 class="left">Pisteet t&auml;n&auml;&auml;n</h3>
							<h3 class="right">Yhteens&auml;</h3>
						</div>
						<div class="rivi_selected">
							<div class="riviteksti">
								<div class="nimi">
									<p>1.Janne</p>
								</div>
								<div class="pisteet">
									<p>13</p>
								</div>
								<div class="yht">
									<p>183</p>
								</div>
							</div>
						</div>
						<div class="rivi">
							<div class="riviteksti">
								<div class="nimi">
									<p>2.Niilo</p>
								</div>
								<div class="pisteet">
									<p>10</p>
								</div>
								<div class="yht">
									<p>173</p>
								</div>
							</div>
						</div>
						<div class="rivi">
							<div class="riviteksti">
								<div class="nimi">
									<p>3.Mauno</p>
								</div>
								<div class="pisteet">
									<p>8</p>
								</div>
								<div class="yht">
									<p>171</p>
								</div>
							</div>
						</div>


					</div>
				</div>
			</div>
			<!--End of column1-->

			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">Omat suoritukset</h2>
					<img class="headerlogo" src="/pics/star.jpg">
				</div>
				<div class="chartbox">
					<c:forEach items="${workouts}" var="workout">
						<div class="top5_content_box">
							<div class="group">
								<a href="/uftc/workout/edit?workoutId=${workout.getId()}"><c:out
										value="${workout.getName()}" /></a>
							</div>
							<div class="points">
								<h5>${workout.getPoints()}</h5>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<!--End if column2-->

			<div id="column3">
				<div class="headerbox">
					<h2 class="headertext">TAPAHTUMAT</h2>
					<img class="headerlogo" src="/uftc/pics/runner.jpg">
				</div>
				<div class="tapahtumat">
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>

				</div>


			</div>
			<!--End of column3-->

		</div>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>